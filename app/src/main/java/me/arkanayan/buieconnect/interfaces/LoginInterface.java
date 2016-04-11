package me.arkanayan.buieconnect.interfaces;

import me.arkanayan.buieconnect.models.AuthResponse;
import me.arkanayan.buieconnect.models.LoginToken;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by arka on 4/9/16.
 */
public interface LoginInterface {

    @POST("register")
    Call<AuthResponse> login(@Body RequestBody loginToken);

}
