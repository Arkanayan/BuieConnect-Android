package me.arkanayan.buieconnect.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {

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

    @NotEmpty
    @Bind(R.id.edit_text_univ_roll)
    TextInputEditText univRollEditText;

    @Bind(R.id.layout_input_first_name)
    TextInputLayout firstNameInputLayout;
    // [End view bindings]
    private EditUserBinding editUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user);
        ActionBar toolbar = getSupportActionBar();

        // set click listeners
        editUserBinding.buttonSubmit.setOnClickListener(this);
        //setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);

        // Instantiate validator
        mEditUserValidator = new Validator(this);
        mEditUserValidator.setValidationListener(this);
        // [ Start user loading/handling ]

        Prefs prefs = Prefs.getInstance(this);
        boolean isUserDetailsPresent = prefs.getBoolean(Prefs.Key.IS_USER_DETAILS_PRESENT);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_AUTH_TOKEN)) {
            //fetch user from auth token here and populate fields
            String authToken = intent.getStringExtra(EXTRA_AUTH_TOKEN);

            UserService userService = new UserService(authToken);
            Call<User> getUserCall = userService.getUserCall();

            final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading your details...");
            progressDialog.show();

            // Load user from network
            getUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    progressDialog.dismiss();
                    mUser = response.body();

                    if (mUser != null && response.isSuccessful()) {
                        //todo bind user to views
                        editUserBinding.setUser(mUser);
                    } else {
                        try {
                            // show error message
                            RestError restError = RestError.getErrorObj(response.errorBody());
                            Toast.makeText(EditUserActivity.this, restError.getMessage(), Toast.LENGTH_SHORT).show();
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
                    progressDialog.dismiss();
                    t.printStackTrace();
                    Toast.makeText(EditUserActivity.this, "Sorry, can't fetch data", Toast.LENGTH_SHORT).show();

                }
            });


        } else if (isUserDetailsPresent){
                try {
                    mUser = User.loadUserFromPreference(this);
                    editUserBinding.setUser(mUser);
                } catch (UserDetailsNotPresent userDetailsNotPresent) {
                    userDetailsNotPresent.printStackTrace();
                    prefs.put(Prefs.Key.IS_USER_DETAILS_PRESENT, false);
                    Toast.makeText(EditUserActivity.this, "User not present", Toast.LENGTH_SHORT).show();
                    // Start login activity
                    Intent loginIntent = LoginActivity.getLoginIntent(this);
                    startActivity(loginIntent);
                    finish();
                }
            }

        // [ End user handling ]

    }


    public static Intent getEditUserIntent(Context context, String authToken) {

        Intent editUserIntent = new Intent(context, EditUserActivity.class);
        editUserIntent.putExtra(EXTRA_AUTH_TOKEN, authToken);

        return editUserIntent;
    }

    public static Intent getEditUserIntent(Context context) {

        return new Intent(context, EditUserActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                mEditUserValidator.validate();
                Log.d(TAG, "onClick submit: firstName " + editUserBinding.firstNameEditText.getText());
                Log.d(TAG, "onClick submit: is_alumus " + editUserBinding.switchAlumnus.isChecked());
                Log.d(TAG, "onClick submit: department " + editUserBinding.spinnerDepartment.getSelectedItem().toString());

        }
    }

    @Override
    public void onValidationSucceeded() {
        Log.d(TAG, "onValidationSucceeded: Validation Succeed");
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}