package com.app.timetable;

import static android.view.View.VISIBLE;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Model.ItemClickListener;
import Model.assignment;
import Model.list_check;
import Model.meeting;
import Model.todo_assignment_RecViewAdapter;
import Model.todo_item;

public class fragment_todo extends Fragment implements ItemClickListener{
    public fragment_todo(){
    }
    fragment_todo_meeting_form todo_meeting_form;
    fragment_todo_assignment_form todo_assignment_form;
    ImageView default_todo_layout;
    RecyclerView todo_meeting;

    //dialog
    FloatingActionButton todo_floating_button;
    RelativeLayout todo_floating_button_background;
    Button todo_meeting_button, todo_assignment_button;

    //data
    ArrayList<meeting> meetings;
    //
    ArrayList<assignment> assignments;
    ArrayList<list_check> list_checks;

    public void setList_checks(ArrayList<list_check> list_checks) {
        this.list_checks = list_checks;
    }

    public void setAssignments(ArrayList<assignment> assignments) {
        this.assignments = assignments;
    }

    public void setMeetings(ArrayList<meeting> meetings) {
        this.meetings = meetings;
    }

    public void set_meet_form(fragment_todo_meeting_form form) {
        todo_meeting_form = form;
    }
    public void set_assignment_form(fragment_todo_assignment_form form) {
        todo_assignment_form = form;
    }

    private static final String TAG = "MyActivity";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View todoView = inflater.inflate(R.layout.fragment_todo, container, false);
        //default_layout
        default_todo_layout = todoView.findViewById(R.id.default_todo_layout);

        todo_meeting = todoView.findViewById(R.id.todo_meet_item_recycleView);

        todo_assignment_RecViewAdapter adapter = new todo_assignment_RecViewAdapter(getActivity(), assignments, list_checks);
        Log.e(TAG, String.valueOf(list_checks));
        todo_meeting.setAdapter(adapter);
        todo_meeting.setLayoutManager(new LinearLayoutManager(todo_meeting.getContext()));

        todo_floating_button = todoView.findViewById(R.id.todo_float_button);
        todo_floating_button_background = todoView.findViewById(R.id.todo_float_button_background);

        todo_meeting_button = todoView.findViewById(R.id.todo_meeting_button);
        todo_assignment_button = todoView.findViewById(R.id.todo_assignment_button);

        todo_meeting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todo_meeting_form).commit();
                    //close_float_button_background();
            }
        });

        todo_assignment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todo_assignment_form).commit();
            }
        });

        todo_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_meeting_button.getVisibility() == View.GONE){
                    open_float_button_background();
                }
                else{
                    close_float_button_background();
                }
            }
        });

        todo_floating_button_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_meeting_button.getVisibility() == VISIBLE){
                    close_float_button_background();
                }
            }
        });

        return todoView;
    }

    private void close_float_button_background(){
        todo_meeting_button.setVisibility(View.GONE);
        todo_assignment_button.setVisibility(View.GONE);

        todo_floating_button_background.getLayoutParams().width = 300;
        todo_floating_button_background.getLayoutParams().height = -2;//wrap_content
        todo_floating_button_background.setBackgroundColor(0);

        todo_floating_button.setImageResource(R.drawable.icon_add);
    }

    private void open_float_button_background(){
        todo_meeting_button.setVisibility(VISIBLE);
        todo_assignment_button.setVisibility(VISIBLE);

        todo_floating_button_background.getLayoutParams().width = -1;//match_parent/match_parent
        todo_floating_button_background.getLayoutParams().height = -1;
        todo_floating_button_background.setBackgroundColor(Color.parseColor("#CC333333"));

        todo_floating_button.setImageResource(R.drawable.icon_close);


    }

    @Override
    public void onClick(View view, meeting meets) {
        Log.e(TAG, "onClick: ");
    }

    private ArrayList<todo_item> createTodo(){
    }
    private int check(String date1, String date2){
        //year
        if(getYear(date1) > getYear(date2)) return 1;
        else if(getYear(date1) < getYear(date2)) return 2;
        else {
            if(getMonth(date1) > getMonth(date2)) return 1;
            else if(getMonth(date1) < getMonth(date2)) return 2;
            else {
                if(getDay(date1) > getDay(date2)) return 1;
                else if(getDay(date1) < getDay(date2)) return 2;
                else {
                    if(getHour(date1) > getMonth(date2)) return 1;
                    else if(getHour(date1) < getHour(date2)) return 2;
                    else {
                        if(getMonth(date1) > getMonth(date2)) return 1;
                        else if(getMonth(date1) < getMonth(date2)) return 2;
                    }
                }
            }
        }
        return 0;
    }
    private int getYear(String date){
        final int i = Integer.parseInt(date.split(" ")[1].split("/")[2]);
        return i;
    }
    private int getMonth(String date){
        final int i = Integer.parseInt(date.split(" ")[1].split("/")[1]);
        return i;
    }
    private int getDay(String date){
        final int i = Integer.parseInt(date.split(" ")[1].split("/")[0]);
        return i;
    }
    private int getHour(String date){
        final int i = Integer.parseInt(date.split(" ")[0].split(":")[0]);
        return i;
    }
    private int getMinus(String date){
        final int i = Integer.parseInt(date.split(" ")[0].split(":")[1]);
        return i;
    }
}
