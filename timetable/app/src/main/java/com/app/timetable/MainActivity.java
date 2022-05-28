package com.app.timetable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

import Model.assignment;
import Model.list_check;
import Model.meeting;

public class MainActivity extends AppCompatActivity implements fragment_calendar.ISendDataListener, fragment_new_subject.AddSubject {
    private SharedPreferences sharedPreferences;

    BottomNavigationView bottomNavigationView;
    private DataBaseHelper dataBaseHelper;
    //calendar
//    ArrayList<Subject> subjectList = new ArrayList<>();
    private fragment_calendar calendarView = new fragment_calendar();
    private fragment_new_subject new_subject = new fragment_new_subject();
    private fragment_calendar_info_subject subject_info = new fragment_calendar_info_subject();
    private LogInFragment logInFragment = new LogInFragment(calendarView);
    //todo
    private fragment_todo todoView = new fragment_todo();
    private fragment_todo_meeting_form meeting_form = new fragment_todo_meeting_form();
    private fragment_todo_assignment_form assignment_form = new fragment_todo_assignment_form();
    // TODO: 18/05/2022
    //data
    private ArrayList<meeting> meetings = new ArrayList<>();
    private ArrayList<assignment> assignments = new ArrayList<>();

    //note
    private fragment_note noteView = new fragment_note();

    //setting
    private fragment_setting settingView = new fragment_setting();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this.getBaseContext());
        sharedPreferences = getSharedPreferences("user_settings", MODE_PRIVATE);

        boolean on_boarding = sharedPreferences.getBoolean("on_boarding", false);

        if(!on_boarding)
        {
            Intent intent = new Intent(MainActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = getSharedPreferences("user_settings", MODE_PRIVATE).edit();

            editor.putBoolean("on_boarding", true);
            editor.commit();
        }


        init();


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
                        bottomNavigationView.setForeground(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, noteView).commit();
                        return true;

                    case R.id.nav_setting:
                        bottomNavigationView.setForeground(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, settingView).commit();
                        return true;
                }
                return false;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, calendarView).commit();

//        FloatingActionButton addNoteBtn = findViewById(R.id.note_add);
//        addNoteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                startActivity(new Intent(MainActivity.this,add_note.class ));
//            }
//        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init(){

        bottomNavigationView = findViewById(R.id.nav_bot);

        //calendar
//        subjectList.add(new Subject("Đại số tuyến tính","H6 305","L01","","17/03/2022",
//                "20/06/2022","9:00","10:50",tmpStudyDay,"",""
//                ,""));
        calendarView.set_new_tkb(new_subject);
        calendarView.setBottomNavigationView(bottomNavigationView);
        new_subject.set_calendar_fragment(calendarView);
        new_subject.setBottomNavigationView(bottomNavigationView);
        calendarView.set_new_tkbbk(logInFragment);
        calendarView.set_info_subject(subject_info);
        subject_info.set_calendar(calendarView);

        //todo

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        meetings = dataBaseHelper.getAllMeet();
        assignments = dataBaseHelper.getAllAssignment();
        todoView.setMeetings(meetings);
        todoView.setAssignments(assignments);

        meeting_form.setTodoView(todoView);
        meeting_form.setBottomNavigationView(bottomNavigationView);
        assignment_form.setTodoView(todoView);
        assignment_form.setBottomNavigationView(bottomNavigationView);

        todoView.setBottomNavigationView(bottomNavigationView);
        todoView.setDataBaseHelper(dataBaseHelper);
        todoView.set_meet_form(meeting_form);
        todoView.set_assignment_form(assignment_form);



        //note

        //setting

    }

    @Override
    public void sendData(Subject subject) {
//        fragment_calendar_info_subject fragment_info = (fragment_calendar_info_subject) getSupportFragmentManager().findFragmentById(R.id.subject_info_layout);
        subject_info.receiveDataFromCalendarFragment(subject);
    }

    @Override
    public void AddSubject(Subject subject) {
        calendarView.getSubjectToList(subject);
    }
}