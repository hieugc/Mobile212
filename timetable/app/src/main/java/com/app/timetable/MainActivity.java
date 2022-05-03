package com.app.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    fragment_calendar calendarView = new fragment_calendar();
    fragment_todo todoView = new fragment_todo();
    fragment_note noteView = new fragment_note();
    fragment_setting settingView = new fragment_setting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_bot);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, calendarView).commit();
                        return true;

                    case R.id.nav_todo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
                        return true;

                    case R.id.nav_note:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, noteView).commit();
                        return true;

                    case R.id.nav_setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, settingView).commit();
                        return true;
                }
                return false;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, calendarView).commit();

    }
}