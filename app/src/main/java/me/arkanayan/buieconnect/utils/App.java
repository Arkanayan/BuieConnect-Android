package me.arkanayan.buieconnect.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.core.CrashlyticsCore;
import com.firebase.client.Firebase;
import com.firebase.client.Logger;

import io.fabric.sdk.android.Fabric;
import me.arkanayan.buieconnect.BuildConfig;

/**
 * Created by arka on 4/20/16.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        // Crashlytics initialize
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        Fabric.with(this, crashlyticsKit);

        // analytics
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Main Screen")
                .putContentType("Screen")
                .putContentId("screen-main"));


        // Initialize firebase
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        //Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);

    }

    public static Context getContext(){
        return mContext;
    }
}
