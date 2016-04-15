package me.arkanayan.buieconnect.interfaces;

import java.util.List;

import me.arkanayan.buieconnect.models.Notice;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by arka on 4/15/16.
 */
public interface NoticesInterface {

    @GET("notices")
    Call<List<Notice>> getNotices();

    @GET("notices/{id}")
    Call<Notice> getNotice(@Path("id") int id);

}
