package com.app.timetable;

import static android.graphics.PorterDuff.Mode;
import static android.view.View.VISIBLE;
import static android.view.View.INVISIBLE;


import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

import Model.ItemClickListener;
import Model.meeting;
import Model.todo_meet_RecViewAdapter;

public class fragment_todo extends Fragment {
    public fragment_todo(){
    }
    ImageView default_todo_layout;
    RecyclerView todo_meeting;

    //dialog
    FloatingActionButton todo_floating_button;
    RelativeLayout todo_floating_button_background;
    Button todo_meeting_button, todo_assignment_button;
    fragment_todo_meeting_form todo_meeting_form;
    fragment_todo_assignment_form todo_assignment_form;


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


        ArrayList<meeting> meetings = new ArrayList<>();

        meetings.add(new meeting(
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "Đại học Bách Khoa",
                "",
                "00:05",
                true
        ));
        meetings.add(new meeting(
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "",
                "abc link",
                "00:05",
                false
        ));
        meetings.add(new meeting(
                "08:00 20/09/2021",
                "Báo cáo đồ án",
                "",
                "",
                "00:05",
                false
        ));
        todo_meet_RecViewAdapter adapter = new todo_meet_RecViewAdapter(todoView.getContext());

        adapter.setMeeting(meetings);

        todo_meeting.setAdapter(adapter);
        todo_meeting.setLayoutManager(new LinearLayoutManager(todoView.getContext()));



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
                if(todo_floating_button_background.getBackgroundTintMode() == Mode.SRC_IN){
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
                if(todo_floating_button_background.getBackgroundTintMode() == Mode.SRC_OVER){
                    close_float_button_background();
                }
            }
        });

        return todoView;
    }

    private void close_float_button_background(){
        todo_floating_button_background.getLayoutParams().width = -2;//wrap_content
        todo_floating_button_background.getLayoutParams().height = -2;//wrap_content
        todo_floating_button_background.setBackgroundTintMode(Mode.SRC_IN);
        todo_floating_button_background.setBackgroundColor(0);

        todo_floating_button.setImageResource(R.drawable.icon_add);

        todo_meeting_button.setVisibility(INVISIBLE);
        todo_assignment_button.setVisibility(INVISIBLE);
    }

    private void open_float_button_background(){
        todo_floating_button_background.getLayoutParams().width = -1;//match_parent/match_parent
        todo_floating_button_background.setBackgroundTintMode(Mode.SRC_OVER);
        todo_floating_button_background.setBackgroundColor(Color.parseColor("#CC333333"));

        todo_floating_button.setImageResource(R.drawable.icon_close);

        todo_meeting_button.setVisibility(VISIBLE);
        todo_assignment_button.setVisibility(VISIBLE);
        todo_floating_button_background.getLayoutParams().height = -1;
    }

}
