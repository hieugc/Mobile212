package com.app.timetable;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class fragment_todo_meeting_form extends Fragment {


    public fragment_todo_meeting_form(){
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain_todo_form, this).commit();
    }
    fragment_todo fragment_todo;

    //info form
    NumberPicker todo_meet_form_day, todo_meet_form_month, todo_meet_form_year, todo_meet_form_hour, todo_meet_form_minus;
    TextInputEditText subtitle, location, link;
    ImageView meet_form_close, todo_meet_form_refresh_time;
    Button meet_form_button_done;
    TextView todo_meet_form_add_time_show, todo_meet_form_line_1_title, todo_meet_form_line_5;

    //dialog
    ConstraintLayout todo_meet_form_add_time_dialog, container_dialog;
    ImageView todo_meet_form_dialog_close;
    NumberPicker meet_form_hour_picker, meet_form_minus_picker;
    Button meet_form_button_dialog_done;

    public void setTodoView(fragment_todo fragment) {
        fragment_todo = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View meeting_form = inflater.inflate(R.layout.fragment_todo_meeting_form, container, false);

        todo_meet_form_line_1_title = meeting_form.findViewById(R.id.todo_meet_form_line_1_title);
        todo_meet_form_line_1_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        todo_meet_form_line_5 = meeting_form.findViewById(R.id.todo_meet_form_line_5);
        todo_meet_form_line_5.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        meet_form_close = meeting_form.findViewById(R.id.todo_meet_form_close);
        //close form
        meet_form_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_form();
            }
        });

        //time
        todo_meet_form_day = meeting_form.findViewById(R.id.todo_meet_form_day);//value
        todo_meet_form_month = meeting_form.findViewById(R.id.todo_meet_form_month);//value
        todo_meet_form_year = meeting_form.findViewById(R.id.todo_meet_form_year);//value
        todo_meet_form_hour = meeting_form.findViewById(R.id.todo_meet_form_hour);//value
        todo_meet_form_minus = meeting_form.findViewById(R.id.todo_meet_form_minus);//value

        setMin_Max(todo_meet_form_day, 1, 31);
        setMin_Max(todo_meet_form_month, 1, 12);
        setMin_Max(todo_meet_form_year, 1970, 2100);
        setMin_Max(todo_meet_form_hour, 0, 23);
        setMin_Max(todo_meet_form_minus, 0, 59);

        Calendar now = Calendar.getInstance();
        todo_meet_form_day.setValue(now.get(Calendar.DAY_OF_MONTH));
        todo_meet_form_month.setValue(now.get(Calendar.MONTH) + 1);
        todo_meet_form_year.setValue(now.get(Calendar.YEAR));
        todo_meet_form_hour.setValue(now.get(Calendar.HOUR_OF_DAY));
        todo_meet_form_minus.setValue(now.get(Calendar.MINUTE));


        subtitle = meeting_form.findViewById(R.id.meeting_form_sub);//value
        location = meeting_form.findViewById(R.id.meeting_form_local);//value
        link = meeting_form.findViewById(R.id.meeting_form_link);//value

        //refresh time init
        todo_meet_form_refresh_time = meeting_form.findViewById(R.id.todo_meet_form_refresh_time);

        //add time init
        todo_meet_form_add_time_show = meeting_form.findViewById(R.id.todo_meet_form_add_time_show);
        todo_meet_form_add_time_dialog = meeting_form.findViewById(R.id.todo_meet_form_add_time_dialog);
        container_dialog = meeting_form.findViewById(R.id.container_dialog);

        //refresh time
        todo_meet_form_refresh_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo_meet_form_add_time_show.setText("00:00");
            }
        });

        //add time
        //open dialog
        todo_meet_form_add_time_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_dialog();
            }
        });

        //select time
        meet_form_hour_picker = meeting_form.findViewById(R.id.meet_form_hour_picker);//value
        meet_form_minus_picker = meeting_form.findViewById(R.id.meet_form_minus_picker);//value
        setMin_Max(meet_form_hour_picker, 0, 23);
        setMin_Max(meet_form_minus_picker, 0, 59);


        meet_form_button_dialog_done = meeting_form.findViewById(R.id.meet_form_button_dialog_done);
        meet_form_button_dialog_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo_meet_form_add_time_show.setText(formatTime(meet_form_hour_picker.getValue(), meet_form_minus_picker.getValue()));
                close_dialog();
            }
        });

        //close dialog
        todo_meet_form_add_time_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_dialog();
            }
        });
        todo_meet_form_dialog_close = meeting_form.findViewById(R.id.todo_meet_form_dialog_close);
        todo_meet_form_dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_dialog();
            }
        });

        //open when click in white background
        container_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_dialog();
            }
        });

        //done form
        meet_form_button_done = meeting_form.findViewById(R.id.meet_form_button_done);
        meet_form_button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String form_time = formatDate(todo_meet_form_day.getValue(), todo_meet_form_month.getValue(), todo_meet_form_year.getValue(), todo_meet_form_hour.getValue(), todo_meet_form_minus.getValue());
                Editable form_title = subtitle.getText();
                Editable form_local = location.getText();
                Editable form_link = link.getText();
                int form_timer_hour = meet_form_hour_picker.getValue();
                int form_timer_minus = meet_form_minus_picker.getValue();
                Log.e("check", form_time);

                //todo: check form
                //todo: create Todo_class

                close_form();
            }
        });


        return meeting_form;
    }

    private void close_form() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragment_todo).commit();
    }

    private void close_dialog(){
        if(todo_meet_form_add_time_dialog.getVisibility() == View.VISIBLE){
            todo_meet_form_add_time_dialog.setVisibility(View.INVISIBLE);
        }
    }

    private void open_dialog(){
        if(todo_meet_form_add_time_dialog.getVisibility() == View.INVISIBLE){
            todo_meet_form_add_time_dialog.setVisibility(View.VISIBLE);
        }
    }

    private void setMin_Max(NumberPicker picker, int min, int max){
        picker.setMinValue(min);
        picker.setMaxValue(max);
    }
    private String formatTime(int hour, int minus){
        String res = "";
        if(hour < 10) res += "0" + Integer.toString(hour) + ":";
        else res += Integer.toString(hour) + ":";

        if(minus < 10) res += "0" + Integer.toString(minus);
        else res += Integer.toString(minus);

        return res;
    }

    private String formatDate(int day,int month,int year,int hour,int minus){
        String res = "";
        if(day < 10) res += "0" + Integer.toString(day) + "/";
        else res += Integer.toString(day) + "/";

        if(month < 10) res += "0" + Integer.toString(month) + "/";
        else res += Integer.toString(month) + "/";

        return formatTime(hour, minus) + " " + res + "/" + Integer.toString(year);
    }// 00:00 dd/mm/yyyy


    //todo
    private String checkTimeForm(int day, int month, int year, int hour, int minus){
        return "";
    }
    //todo
    private Boolean checkSubtitleForm(String input){
        return false;
    }
    //todo
    private Boolean checkLocationForm(String input){
        return false;
    }
    //todo
    private Boolean checkLinkForm(String input){
        return false;
    }
}
