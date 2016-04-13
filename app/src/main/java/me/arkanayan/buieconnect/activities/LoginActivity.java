package me.arkanayan.buieconnect.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.models.AuthResponse;
import me.arkanayan.buieconnect.models.RestError;
import me.arkanayan.buieconnect.services.LoginService;
import me.arkanayan.buieconnect.utils.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    final String TAG = this.getClass().getSimpleName();
    final int RC_SIGN_IN = 9002;

    @Bind(R.id.sign_in_button) SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Log.v(TAG, "app id: " + getString(R.string.server_client_id));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setStyle(SignInButton.SIZE_WIDE, SignInButton.COLOR_LIGHT);
        signInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult: " + result.isSuccess());
        LoginService loginService = new LoginService();
        if (result.isSuccess()) {
            // Signed in successfully
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d(TAG, "handleSignInResult: Name: " + acct.getDisplayName());
            Log.d(TAG, "handleSignInResult: idToken: " + acct.getIdToken());

            Call<AuthResponse> responseCall = loginService.getLoginCall(acct.getIdToken());

            final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            responseCall.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    progressDialog.dismiss();
                    AuthResponse authResponse = response.body();
                    if (authResponse != null && response.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: authtoken: " + authResponse.getAuthToken());

                        // Update shared preferences
                        Prefs.getInstance(LoginActivity.this).put(Prefs.Key.AUTH_TOKEN, authResponse.getAuthToken());
                        Prefs.getInstance().put(Prefs.Key.IS_LOGGED_IN, true);

                        if (authResponse.getFirstTime()) {
                            Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                            // todo launch edit user activity here
                        } else {
                            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainActivity);
                            finish();
                        }
                    } else {

                        try {
                            RestError error = RestError.getErrorObj(response.errorBody());
                            Log.d(TAG, "onResponse: Errorbody: " + error.getMessage());
                            Toast.makeText(LoginActivity.this, "Error, " + error.getMessage() , Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, "Sorry, Login Failed", Toast.LENGTH_SHORT).show();

    }

    public static Intent getLoginIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
