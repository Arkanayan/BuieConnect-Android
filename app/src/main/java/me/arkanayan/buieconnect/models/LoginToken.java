package me.arkanayan.buieconnect.models;

/**
 * Created by arka on 4/9/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginToken
{

    public LoginToken(String idToken) {
        this.idToken = idToken;
    }

    @SerializedName("id_token")
    @Expose
    private String idToken;

    /**
     *
     * @return
     * The idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     *
     * @param idToken
     * The id_token
     */
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

}