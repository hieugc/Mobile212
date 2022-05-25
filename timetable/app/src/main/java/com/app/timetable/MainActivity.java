package com.app.timetable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
    //calendar
//    ArrayList<Subject> subjectList = new ArrayList<>();
    fragment_calendar calendarView = new fragment_calendar();
    fragment_new_subject new_subject = new fragment_new_subject();
    fragment_calendar_info_subject subject_info = new fragment_calendar_info_subject();
    LogInFragment logInFragment = new LogInFragment();
    //todo
    fragment_todo todoView = new fragment_todo();
    fragment_todo_meeting_form meeting_form = new fragment_todo_meeting_form();
    fragment_todo_assignment_form assignment_form = new fragment_todo_assignment_form();
    // TODO: 18/05/2022
    //data
    ArrayList<meeting> meetings = new ArrayList<>();
    ArrayList<assignment> assignments = new ArrayList<>();
    ArrayList<list_check> list_checks = new ArrayList<>();

    //note
    fragment_note noteView = new fragment_note();

    //setting
    fragment_setting settingView = new fragment_setting();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_main);

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
        //calendar
//        subjectList.add(new Subject("Đại số tuyến tính","H6 305","L01","","17/03/2022",
//                "20/06/2022","9:00","10:50",tmpStudyDay,"",""
//                ,""));
        calendarView.set_new_tkb(new_subject);
        new_subject.set_calendar_fragment(calendarView);
        calendarView.set_new_tkbbk(logInFragment);
        calendarView.set_info_subject(subject_info);
        subject_info.set_calendar(calendarView);

        //todo
        //init_data();
        meeting_form.setTodoView(todoView);
        assignment_form.setTodoView(todoView);
        todoView.set_meet_form(meeting_form);
        todoView.set_assignment_form(assignment_form);

        //data
        todoView.setMeetings(meetings);
        todoView.setAssignments(assignments);

        //note

        //setting

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init_data(){
        meetings.add(new meeting(
                1,
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                1,
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                1,
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                1,
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                1,
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                1,
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                2,
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                11,
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));

        list_checks.add(new list_check(
                1,
                "Thiet ke mockup",
                false,
                1
        ));
        list_checks.add(new list_check(
                2,
                "Thiet ke mockup B",
                false,
                1
        ));
        list_checks.add(new list_check(
                3,
                "Thiet ke mockup A",
                false,
                2
        ));
        list_checks.add(new list_check(
                4,
                "Thiet ke mockup abc",
                false,
                2
        ));
        list_checks.add(new list_check(
                6,
                "Thiet ke mockup abc",
                false,
                2
        ));
        list_checks.add(new list_check(
                7,
                "Thiet ke mockup abc",
                false,
                2
        ));
        list_checks.add(new list_check(
                8,
                "Thiet ke mockup abc",
                false,
                2
        ));

        assignments.add(new assignment(
                1,
                "BTL 1",
                "10/11/2019",
                "10/11/2022",
                false
        ));
        assignments.add(new assignment(
                2,
                "BTL 2",
                "10/11/2021",
                "10/11/2022",
                false
        ));
        for (assignment a: assignments){
            a.setList_checks(classify_list_check(a.getId()));
        }
    }
    private ArrayList<list_check> classify_list_check(int id){
        ArrayList<list_check> list_checks = new ArrayList<>();
        for (list_check check: this.list_checks){
            if (check.getAssign() == id){
                list_checks.add(check);
            }
        }
        return list_checks;
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