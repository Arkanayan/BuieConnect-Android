package me.arkanayan.buieconnect.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import co.mobiwise.fastgcm.GCMListenerService;
import in.uncod.android.bypass.Bypass;
import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.activities.MainActivity;
import me.arkanayan.buieconnect.activities.NoticeDetailActivity;
import me.arkanayan.buieconnect.models.Notice;
import me.arkanayan.buieconnect.utils.App;

public class MyGCMListenerService extends GCMListenerService {
    private final String TAG = this.getClass().getSimpleName();


    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        if (data.getString("type").equals("notice")) {
            Gson gson = new Gson();
            Notice notice = gson.fromJson(data.getString("data"), Notice.class);
            Log.d(TAG, "***Bundle Keys****");
            for (String key : data.keySet()) {
                Log.d(TAG, key + " : " + data.get(key));
            }
            String message = notice.getMessage();
            String title = notice.getTitle();
            // sends notification
            sendNotification(notice);
        }
    }

    /**
     *  Shows a simple notification
     * @param notice GCM message Received
     */
    private void sendNotification(Notice notice) {
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent intent = NoticeDetailActivity.getInstance(this, notice);

/*        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,
                intent, PendingIntent.FLAG_ONE_SHOT);*/
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{backIntent, intent}, PendingIntent.FLAG_ONE_SHOT);

        Bypass bypass = new Bypass(this);
        CharSequence message = bypass.markdownToSpannable(notice.getMessage());

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.buie_splash);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notice.getTitle())
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
