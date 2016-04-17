package me.arkanayan.buieconnect.utils;

import android.content.Context;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by arka on 4/8/16.
 */
public class Utils {

    public static RequestBody getRequestBodyFromModel(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        return requestBody;
    }
//
//    public static CharSequence getMarkdownToString(String string) {
//        String messageMarkdownString = string;
//        CharSequence messageString = mBypass.markdownToSpannable(messageMarkdownString);
//    }

}
