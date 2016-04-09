package me.arkanayan.buieconnect.services;


import android.util.Log;

import com.google.gson.Gson;

import me.arkanayan.buieconnect.ServiceGenerator;
import me.arkanayan.buieconnect.interfaces.Login;
import me.arkanayan.buieconnect.pojo.AuthResponse;
import me.arkanayan.buieconnect.pojo.LoginToken;
import me.arkanayan.buieconnect.pojo.RestError;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arka on 4/9/16.
 */
public class LoginService {

    private String TAG = this.getClass().getSimpleName();

    private Login login;

    public LoginService() {
        this.login = ServiceGenerator.createService(Login.class);
    }

    public Call<AuthResponse> getLoginCall(String idToken) {

        LoginToken token = new LoginToken(idToken);

        Gson gson = new Gson();
        String json = gson.toJson(token);
        Log.d(TAG, "doLogin: Gson json: " + json);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<AuthResponse> responseCall = login.login(requestBody);

        return responseCall;
    }


}
