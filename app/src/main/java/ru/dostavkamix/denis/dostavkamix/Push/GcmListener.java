package ru.dostavkamix.denis.dostavkamix.Push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by d.tkachenko on 30.04.2016.
 */
public class GcmListener extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d("Push", "From: " + from);
        Log.d("Push", "Massege: " + message);

        int mNotificationId = 001;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                AppController.getInstance());
        Notification notification = mBuilder
                .setSmallIcon(R.drawable.logo_512)
                .setTicker("Доставка MIX").setWhen(0)
                .setAutoCancel(true)
                .setContentTitle("Доставка MIX")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(message).build();

        NotificationManager notificationManager = (NotificationManager) AppController.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

}


