package com.app.timetable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AssignmentAlarmReceiver extends BroadcastReceiver {
    private SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedPreferences = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        boolean notification = sharedPreferences.getBoolean("notification_bar", true);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Assignment")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(intent.getStringExtra("titleExtra"))
                .setContentText(intent.getStringExtra("messageExtra"))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if(!notification)
            return;

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(345, builder.build());
    }
}
