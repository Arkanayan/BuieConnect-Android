package me.arkanayan.buieconnect.models;

/**
 * Created by arka on 4/9/16.
 */

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("first_time")
    @Expose
    private Boolean firstTime;

    /**
     *
     * @return
     * The authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     *
     * @return
     * The firstTime
     */
    public Boolean getFirstTime() {
        return firstTime;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "authToken='" + authToken + '\'' +
                ", firstTime=" + firstTime +
                '}';
    }
}