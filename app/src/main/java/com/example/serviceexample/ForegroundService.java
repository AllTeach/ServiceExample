package com.example.serviceexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {

    public ForegroundService() {
    }

    public static final String channelID = "channelID123456";
    public static final String channelName = "Channel Name";
    private static final String CHANNEL_DEFAULT_IMPORTANCE = "1";
    private static final int ONGOING_NOTIFICATION_ID = 1;
    private static final String CHANNEL_DESCRIPTION = "description";
    private static final String TAG = "Foreground Service";
    private Notification notification;
    private NotificationManager notificationManager;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 1) create notification channel
        createNotificationChannel();

        // 2) create notification
        createNotification();




        //3) start service as foreground
        startForeground(ONGOING_NOTIFICATION_ID,notification);

        return START_NOT_STICKY;
    }

    private void createNotification()
    {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this.getApplicationContext(), channelID);
        nb.setContentIntent(pendingIntent);
        nb.setContentTitle("Foreground Location Service");
        nb.setSmallIcon(R.drawable.ic_launcher_foreground);
        nb.setChannelId(channelID);
        nb.setOnlyAlertOnce(true);
        notification = nb.build();
        Log.d(TAG, "createNotification: ");




    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channelName;
            String description = CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);

            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "createNotificationChannel: ");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}