package com.app.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class fragment_setting extends Fragment {

    private static final long MILLIS_IN_AN_HOUR = 1000 * 60 * 60;
    private static final long MILLIS_IN_AN_MINUTE = 1000 * 60;
    public fragment_setting(){}
    RelativeLayout layout4;
    private CheckBox checkBox;
    private SharedPreferences preferences;
    private RelativeLayout user_layout;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 =  inflater.inflate(R.layout.fragment_setting, container, false);
        preferences = getActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        user_layout = view1.findViewById(R.id.layout3);
        layout4 = view1.findViewById(R.id.layout4);

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(),OnBoardingActivity.class);
                startActivity(intent);
            }
        });
        user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext(), R.style.RoundShapeTheme);
                builder.setTitle("Xoá liên kết tài khoản?");
                builder.setMessage("Bạn chắc chắn muốn xoá liên kết tài khoản Bách Khoa hiện tại?");
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Huỷ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Xoá liên kết thành công", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("username");
                        editor.remove("password");
                        editor.commit();
                    }
                });
                builder.show();
            }
        });

        return view1;
    }



    public void setAlarm(int id, String date, String time)
    {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity() , AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Date selectedDate = new Date();
        LocalTime localTime = null;
        try {
            selectedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int hour = 0,minute = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            hour = localTime.get(ChronoField.HOUR_OF_DAY);
            minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(selectedDate);
        Log.e("day", ""+calendar.get(Calendar.DAY_OF_MONTH));
        Log.e("month", ""+(calendar.get(Calendar.MONTH) + 1));
        Log.e("day", ""+calendar.get(Calendar.YEAR));
        Log.e("hour", ""+calendar.get(Calendar.HOUR_OF_DAY));
        Log.e("minute", ""+calendar.get(Calendar.MINUTE));
        Log.e("hour", ""+hour);
        Log.e("minute", ""+minute);
        calendar.setTimeInMillis(calendar.getTimeInMillis()-hour*MILLIS_IN_AN_HOUR-minute*MILLIS_IN_AN_MINUTE);
        Log.e("day", ""+calendar.get(Calendar.DAY_OF_MONTH));
        Log.e("month", ""+(calendar.get(Calendar.MONTH) + 1));
        Log.e("day", ""+calendar.get(Calendar.YEAR));
        Log.e("hour", ""+calendar.get(Calendar.HOUR_OF_DAY));
        Log.e("minute", ""+calendar.get(Calendar.MINUTE));
        Log.e("calendar", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(calendar.getTime()));
        Log.e("calendar", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(calendar.getTimeInMillis())));

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Log.e("alarm","SET ALARM SUCCESSFULLY");
    }

    public void cancelAlarm(int id)
    {
        Intent intent = new Intent(getActivity() , AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        if(alarmManager == null)
        {
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Log.e("alarm", "CANCEL ALARM SUCCESSFULLY");
    }

}
