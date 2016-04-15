package me.arkanayan.buieconnect.apis;

import me.arkanayan.buieconnect.models.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by arka on 4/10/16.
 */
public interface UserApi {

    @GET("user")
    Call<User> getUser();

    @PUT("user")
    Call<User> updateUser(@Body RequestBody user);
}
