package com.app.timetable;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class fragment_calendar extends Fragment {
    public fragment_calendar(){}

    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private ArrayList<Subject> subjects;
    private FloatingActionButton floatingActionButton;
    private Button calendar_tkbbk_button, calendar_tkb_button;
    private RelativeLayout calendar_float_button_background;
    private fragment_new_subject tkb_new_subject;
    private LogInFragment logInFragment;


    public void set_new_tkb(fragment_new_subject form) {
        tkb_new_subject = form;
    }
    public void set_new_tkbbk(LogInFragment logInFragment) {
        this.logInFragment = logInFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View calendarView = inflater.inflate(R.layout.fragment_calendar, container, false);
        recyclerView = (RecyclerView) calendarView.findViewById(R.id.tkb_list);

        floatingActionButton = calendarView.findViewById(R.id.calendar_float_button);
        calendar_tkb_button = calendarView.findViewById(R.id.calendar_tkb_button);
        calendar_tkbbk_button = calendarView.findViewById(R.id.calendar_tkbbk_button);
        calendar_float_button_background = calendarView.findViewById(R.id.calendar_float_button_background);

        calendar_tkb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,tkb_new_subject).commit();
            }
        });

        calendar_tkbbk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, logInFragment).commit();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (calendar_tkbbk_button.getVisibility() == GONE){
                    open_float_button_background();
                }
                else{
                    close_float_button_background();
                }
            }
        });

        calendar_float_button_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendar_tkbbk_button.getVisibility() == VISIBLE){
                    close_float_button_background();
                }
            }
        });

        subjects = new ArrayList<Subject>();
        subjects.add(new Subject("Giải tích 2","7:00","9:50",""));
        subjects.add(new Subject("Đại số tuyến tính","7:00","9:50",""));
        subjects.add(new Subject("Giáo dục thể chất","10:00","11:50","Note.."));
        subjects.add(new Subject("Hệ thống số","7:00","9:50",""));
        subjects.add(new Subject("Hệ thống số (Lab)","7:00","9:50",""));
        subjects.add(new Subject("Hệ thống số (Lab)","7:00","9:50",""));

        adapter = new SubjectAdapter(subjects,calendarView.getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(calendarView.getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return calendarView;
    }

    private void close_float_button_background(){
        calendar_float_button_background.getLayoutParams().width = -2;//wrap_content
        calendar_float_button_background.getLayoutParams().height = -2;//wrap_content
        calendar_float_button_background.setBackgroundColor(0);

        floatingActionButton.setImageResource(R.drawable.icon_add);

        calendar_tkb_button.setVisibility(View.GONE);
        calendar_tkbbk_button.setVisibility(View.GONE);
    }

    private void open_float_button_background(){
        calendar_float_button_background.getLayoutParams().width = -1;//match_parent/
        calendar_float_button_background.getLayoutParams().height = -1;
        calendar_float_button_background.setBackgroundColor(Color.parseColor("#CC333333"));

        floatingActionButton.setImageResource(R.drawable.icon_close);

        calendar_tkb_button.setVisibility(VISIBLE);
        calendar_tkbbk_button.setVisibility(VISIBLE);
    }

}
