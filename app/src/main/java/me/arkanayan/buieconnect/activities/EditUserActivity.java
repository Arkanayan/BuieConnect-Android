package me.arkanayan.buieconnect.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.arkanayan.buieconnect.BuildConfig;
import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.databinding.EditUserBinding;
import me.arkanayan.buieconnect.exceptions.UserDetailsNotPresent;
import me.arkanayan.buieconnect.models.RestError;
import me.arkanayan.buieconnect.models.User;
import me.arkanayan.buieconnect.services.UserService;
import me.arkanayan.buieconnect.utils.Prefs;
import me.arkanayan.buieconnect.utils.Utils;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditUserActivity extends AppCompatActivity implements Validator.ValidationListener {

    public final String TAG = this.getClass().getSimpleName();

    public static final String EXTRA_AUTH_TOKEN = BuildConfig.APPLICATION_ID + ".extra_auth_token";
    private User mUser;
    private Validator mEditUserValidator;

    // [Start view bindings]
    @NotEmpty
    @Bind(R.id.first_name_edit_text)
    TextInputEditText firstNameEditText;

    @NotEmpty
    @Bind(R.id.edit_text_last_name)
    TextInputEditText lastNameEditText;

    @NotEmpty
    @Email
    @Bind(R.id.edit_text_email)
    TextInputEditText emailEditText;

    @NotEmpty
    @Max(2030)
    @Min(1994)
    @Bind(R.id.edit_text_passout_year)
    TextInputEditText passoutYearEditText;

    @NotEmpty
    @Max(2030)
    @Min(1994)
    @Bind(R.id.edit_text_admission_year)
    TextInputEditText admissionYearEditText;

    @Optional
    @Bind(R.id.edit_text_univ_roll)
    TextInputEditText univRollEditText;

    @Bind(R.id.layout_input_first_name)
    TextInputLayout firstNameInputLayout;
    // [End view bindings]
    private EditUserBinding editUserBinding;
    private Prefs mPref;
    private String mAuthToken;
    private UserService mUserService;

    private boolean mFirsTimeEdit = false;

    private ImageView mSaveItem;
    ProgressDialog mProgressDialog;


   // public TourGuide mTutorialHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user);

        mActivity = this;

        final ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Edit your details");

        // set click listeners
       //   editUserBinding.buttonSubmit.setOnClickListener(this);
        //setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);

        // analytics
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Edit User Screen")
                .putContentType("Screen")
                .putContentId("screen-edit-user"));

        // Instantiate validator
        mEditUserValidator = new Validator(this);
        mEditUserValidator.setValidationListener(this);
        // [ Start user loading/handling ]
        mPref = Prefs.getInstance(this);
        // Initialize user service

        mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);

        // save action item
        // mSaveItem = (MenuItem) findViewById(R.id.action_save);

        boolean isUserDetailsPresent = mPref.getBoolean(Prefs.Key.IS_USER_DETAILS_PRESENT);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_AUTH_TOKEN)) {

            toolbar.setDisplayHomeAsUpEnabled(false);
            mFirsTimeEdit = true;
            //fetch user from auth token here and populate fields
            mAuthToken = intent.getStringExtra(EXTRA_AUTH_TOKEN);
            mPref.put(Prefs.Key.AUTH_TOKEN, mAuthToken);

            mUserService = new UserService(mAuthToken);

            Call<User> getUserCall = mUserService.getUserCall();

            // final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading your details...");
            mProgressDialog.show();

            // Load user from network
            getUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    mProgressDialog.dismiss();
                    mUser = response.body();

                    if (mUser != null && response.isSuccessful()) {
                        Log.d(TAG, "onResponse: Reg Date: " + mUser.getRegDate().toString());

                        editUserBinding.setUser(mUser);
                        // Analytics
                        Answers.getInstance().logCustom(new CustomEvent("User Retrived")
                                .putCustomAttribute("Status", "Success")
                                .putCustomAttribute("Cause", "Login"));
                    } else {
                        try {
                            // show error message
                            RestError restError = RestError.getErrorObj(response.errorBody());
                            Toast.makeText(EditUserActivity.this, restError.getMessage(), Toast.LENGTH_SHORT).show();
                            // Analytics
                            Answers.getInstance().logCustom(new CustomEvent("User Retrived")
                                    .putCustomAttribute("Status", "Failed")
                                    .putCustomAttribute("Cause", restError.getMessage()));

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse:failed: " + e.toString());
                            Toast.makeText(EditUserActivity.this, "Sorry, unable to fetch data", Toast.LENGTH_SHORT).show();

                        }
                        // Start login activity on user load failure
                        startActivity(LoginActivity.getLoginIntent(getBaseContext()));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    mProgressDialog.dismiss();
                    t.printStackTrace();
                    Toast.makeText(EditUserActivity.this, "Sorry, can't fetch data", Toast.LENGTH_SHORT).show();
                    // Analytics
                    Answers.getInstance().logCustom(new CustomEvent("User Retrived")
                            .putCustomAttribute("Status", "Failed")
                            .putCustomAttribute("Cause", t.getMessage()));
                }
            });


        } else if (isUserDetailsPresent){
                mFirsTimeEdit = false;
                try {
                    mAuthToken = mPref.getString(Prefs.Key.AUTH_TOKEN);
                    mPref.put(Prefs.Key.AUTH_TOKEN, mAuthToken);
                    mUserService = new UserService(mAuthToken);
                    mUser = User.loadUserFromPreference(this);
                    editUserBinding.setUser(mUser);
                } catch (UserDetailsNotPresent userDetailsNotPresent) {
                    userDetailsNotPresent.printStackTrace();
                    mPref.put(Prefs.Key.IS_USER_DETAILS_PRESENT, false);
                    Toast.makeText(EditUserActivity.this, "User not present", Toast.LENGTH_SHORT).show();
                    // Start login activity
                    Intent loginIntent = LoginActivity.getLoginIntent(this);
                    startActivity(loginIntent);
                    finish();
                }
            }

        // [ End user handling ]

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mFirsTimeEdit) {
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }

    public static Intent getEditUserIntent(Context context, String authToken) {

        Intent editUserIntent = new Intent(context, EditUserActivity.class);
        editUserIntent.putExtra(EXTRA_AUTH_TOKEN, authToken);

        return editUserIntent;
    }

    public static Intent getEditUserIntent(Context context) {

        return new Intent(context, EditUserActivity.class);
    }

/*
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                mEditUserValidator.validate();
*/
/*                Log.d(TAG, "onClick submit: firstName " + firstNameEditText.getText());
                Log.d(TAG, "onClick submit: is_alumus " + editUserBinding.switchAlumnus.isChecked());
                Log.d(TAG, "onClick submit: department " + editUserBinding.spinnerDepartment.getSelectedItem().toString());
                Log.d(TAG, "onClick submit: univ Roll:  " + editUserBinding.editTextUnivRoll.getText());*//*


        }
    }
*/

    @Override
    public void onValidationSucceeded() {
        Log.d(TAG, "onValidationSucceeded: Validation Succeed");
        //todo handle user edit here
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        Long univRoll = univRollEditText.getText().toString().equals("") ? 0 : Long.parseLong(univRollEditText.getText().toString());
        int admissionYear = Integer.parseInt(admissionYearEditText.getText().toString().trim());
        int passoutYear = Integer.parseInt(passoutYearEditText.getText().toString().trim());
        boolean isAlumnus = editUserBinding.switchAlumnus.isChecked();
        String deptName =  editUserBinding.spinnerDepartment.getSelectedItem().toString().trim();
        int currentSemester = Integer.parseInt(editUserBinding.spinnerSemester.getSelectedItem().toString());


        mUser.setFirstName(firstName);
        mUser.setLastName(lastName);
        mUser.setEmail(email);
        mUser.setUnivRoll(univRoll);
        mUser.setAdmissionYear(admissionYear);
        mUser.setPassoutYear(passoutYear);
        mUser.setIsAlumnus(isAlumnus);
        mUser.setDepartmentName(deptName);
        mUser.setCurrentSemester(currentSemester);

        //todo get and set gcm reg token here

        RequestBody userRequestBody = Utils.getRequestBodyFromModel(mUser);
        Call<User> updateUserCall = mUserService.updateUser(userRequestBody);

        //final ProgressDialog userEditProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Saving new details...");
        mProgressDialog.show();

        updateUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mProgressDialog.dismiss();

                if (response.isSuccessful()) {
                    User.storeUser(EditUserActivity.this, mUser);
                    Toast.makeText(EditUserActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                    // Analytics
                    Answers.getInstance().logCustom(new CustomEvent("User Edit")
                                        .putCustomAttribute("Edit status", "Success"));
                } else {
                    try {

                        RestError error = RestError.getErrorObj(response.errorBody());
                        showErrorAndRetry("Error, " + error.getMessage());

                        // Analytics
                        Answers.getInstance().logCustom(new CustomEvent("User Edit")
                                .putCustomAttribute("Edit status", "Failed")
                                .putCustomAttribute("Cause", error.getMessage()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.d(TAG, "onFailure: User update: " + t.getCause());
                // Analytics
                Answers.getInstance().logCustom(new CustomEvent("User Edit")
                        .putCustomAttribute("Edit status", "Failed")
                        .putCustomAttribute("Cause", t.getMessage()));

                showErrorAndRetry("Sorry, failed to update ");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mProgressDialog.dismiss();
    }


  //  public TourGuide mTutorialHandler;
    private Activity mActivity;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
         getMenuInflater().inflate(R.menu.edit_user_menu, menu);

        if (mFirsTimeEdit) {
            final AlertDialog alertDialog = new AlertDialog.Builder(EditUserActivity.this).create();
            alertDialog.setTitle("Instructions");
            alertDialog.setMessage("After entering your info, press save then hit back button.");
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Got it",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

/*        MenuItem menuItem = menu.findItem(R.id.action_save);
        final ImageView button = (ImageView) menuItem.getActionView();

        // just adding some padding to look better
        float density = mActivity.getResources().getDisplayMetrics().density;
        int padding = (int)(5 * density);
        button.setPadding(padding, padding, padding, padding);

        // set an image
        button.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_save_white));

        ToolTip toolTip = new ToolTip()
                .setTitle("Welcome!")
                .setDescription("Click on Get Started to begin...")
                .setGravity(Gravity.LEFT|Gravity.BOTTOM);

        mTutorialHandler = TourGuide.init(this).with(TourGuide.Technique.HorizontalRight)
                .motionType(TourGuide.MotionType.ClickOnly)
                .setPointer(new Pointer())
                .setToolTip(toolTip)
                .setOverlay(new Overlay())
                .playOn(button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mTutorialHandler.cleanUp();
            }
        });*/
/*
        new Handler().post(new Runnable() {
            @Override
            public void run() {
//                MenuItem menuItem = menu.getItem(0);
//                mSaveItem = (ImageView) menuItem.getActionView();



            }
        });
*/

       // mSaveItem = (ImageView) menu.findItem(R.id.action_save).getActionView();

/*        ToolTip toolTip = new ToolTip()
                .setTitle("Welcome!")
                .setDescription("Click on Get Started to begin...")
                .setGravity(Gravity.LEFT|Gravity.BOTTOM);

        mTutorialHandler = TourGuide.init(this).with(TourGuide.Technique.Click)
                .motionType(TourGuide.MotionType.ClickOnly)
                .setPointer(new Pointer())
                .setToolTip(toolTip)
                .setOverlay(new Overlay())
                .playOn(mSaveItem);*/

         return super.onCreateOptionsMenu(menu);
        //return true;
    }

    public View getSaveActionView() {
        Window window = getWindow();
        View v = window.getDecorView();
        return v.findViewById(R.id.action_save);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                mEditUserValidator.validate();
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showErrorAndRetry(String errorMessage) {
        Snackbar.make(editUserBinding.scrollView, errorMessage, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.CYAN)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEditUserValidator.validate();
                    }
                }).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
/*                if (view.getParent() instanceof TextInputLayout){
                    ((TextInputLayout) view.getParent()).setError(message);
                }
                else {*/
                    ((EditText) view).setError(message);
                // }
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
