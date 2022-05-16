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
    //calendar
    fragment_calendar calendarView = new fragment_calendar();

    //todo
    fragment_todo todoView = new fragment_todo();
    fragment_todo_meeting_form meeting_form = new fragment_todo_meeting_form();
    fragment_todo_assignment_form assignment_form = new fragment_todo_assignment_form();

    //note
    fragment_note noteView = new fragment_note();

    //setting
    fragment_setting settingView = new fragment_setting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_main);

        init();

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

    private void init(){
        //calendar

        //todo
        todoView.set_meet_form(meeting_form);
        meeting_form.setTodoView(todoView);
        todoView.set_assignment_form(assignment_form);
        assignment_form.setTodoView(todoView);

        //note

        //setting

    }

}