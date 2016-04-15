package me.arkanayan.buieconnect.apis;

import me.arkanayan.buieconnect.models.AuthResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by arka on 4/9/16.
 */
public interface LoginApi {

    @POST("register")
    Call<AuthResponse> login(@Body RequestBody loginToken);

}
