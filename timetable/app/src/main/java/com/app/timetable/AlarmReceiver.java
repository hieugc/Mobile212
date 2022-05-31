package com.app.timetable;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private SharedPreferences sharedPreferences;
    @Override
    public void onReceive(Context context, Intent intent) {

//        Intent i = new Intent(context, MainActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i , PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        sharedPreferences = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        boolean notification = sharedPreferences.getBoolean("notification_bar", true);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "TimeTable")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(intent.getStringExtra("titleExtra"))
                .setContentText(intent.getStringExtra("messageExtra"))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
//                .setContentIntent(pendingIntent);

        if(!notification)
            return;

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }

}
