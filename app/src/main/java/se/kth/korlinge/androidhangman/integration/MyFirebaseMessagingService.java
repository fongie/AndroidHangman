package se.kth.korlinge.androidhangman.integration;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Service that handles Firebase notifications
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String channelId = "CH";
    private static final String channel = "CHANNEL";

    private NotificationManager notificationManager;

    /**
     * Triggers on receiving a message from Firebase
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            setupChannel();


        int noteId = new Random().nextInt(5000);
        Log.e("notified", remoteMessage.getNotification().getBody());
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.support.v4.R.drawable.notification_template_icon_bg)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody());

        notificationManager.notify(noteId,notificationBuilder.build());
    }

    //for api > 26 apparently this complicated channel thing is absolutely required
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannel() {
        CharSequence adminChannel = channel;
        NotificationChannel channel;
        channel = new NotificationChannel(channelId, adminChannel, NotificationManager.IMPORTANCE_LOW);
        channel.setDescription("notification channel");
        channel.enableLights(true);
        channel.setLightColor(Color.GREEN);
        channel.enableVibration(false);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }

    }
}
