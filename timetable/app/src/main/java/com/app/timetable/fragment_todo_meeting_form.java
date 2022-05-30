package com.app.timetable;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class fragment_todo_meeting_form extends Fragment {


    public fragment_todo_meeting_form(){
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain_todo_form, this).commit();
    }
    private fragment_todo fragment_todo;

    //info form
    private NumberPicker todo_meet_form_day, todo_meet_form_month, todo_meet_form_year, todo_meet_form_hour, todo_meet_form_minus;
    private TextInputEditText subtitle, location, link;
    private ImageView meet_form_close, todo_meet_form_refresh_time;
    private Button meet_form_button_done, meet_form_button_remove;
    private TextView todo_meet_form_add_time_show, todo_meet_form_line_1_title, todo_meet_form_line_5;

    //dialog
    private ConstraintLayout todo_meet_form_add_time_dialog, container_dialog;
    private ImageView todo_meet_form_dialog_close;
    private NumberPicker meet_form_hour_picker, meet_form_minus_picker;
    private Button meet_form_button_dialog_done;


    private BottomNavigationView bottomNavigationView;

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public void setTodoView(fragment_todo fragment) {
        fragment_todo = fragment;
    }

    public com.app.timetable.fragment_todo getFragment_todo() {
        return fragment_todo;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bottomNavigationView.setForeground(null);
        // Inflate the layout for this fragment
        View meeting_form = inflater.inflate(R.layout.fragment_todo_meeting_form, container, false);
        init(meeting_form);

        //close form
        meet_form_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_form();
            }
        });

        subtitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(subtitle.getError() != null && !subtitle.isFocused())
                {
                    subtitle.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(subtitle.getError() != null)
                {
                    subtitle.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().equals(""))
                {
                    subtitle.setError("Hãy nhập nội dụng cuộc họp");
                }
            }
        });
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(location.getError() != null && !location.isFocused())
                {
                    location.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(location.getError() != null)
                {
                    location.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().equals("") && link.getText().toString().trim().equals(""))
                {
                    location.setError("Hãy nhập địa điểm/link cuộc họp");
                }
            }
        });

        link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(link.getError() != null && !link.isFocused())
                {
                    link.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(link.getError() != null)
                {
                    link.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().equals("") && location.getText().toString().trim().equals(""))
                {
                    link.setError("Hãy nhập địa điểm/link cuộc họp");
                }
            }
        });

        //refresh time
        todo_meet_form_refresh_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo_meet_form_add_time_show.setText("00:05");
            }
        });

        //add time
        //open dialog
        todo_meet_form_add_time_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meet_form_hour_picker.setValue(Integer.parseInt(todo_meet_form_add_time_show.getText().toString().trim().split(":")[0]));
                meet_form_minus_picker.setValue(Integer.parseInt(todo_meet_form_add_time_show.getText().toString().trim().split(":")[1]));
                open_dialog();
            }
        });


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


        return meeting_form;
    }

    @Override
    public void onStart() {
        super.onStart();

        String done = "";
        Bundle bundle = getArguments();
        if (bundle != null){
            String func = bundle.getString("func");
            if(func == "edit_meeting"){
                CharSequence title = bundle.getString("title").trim();
                CharSequence location = bundle.getString("location").trim();
                CharSequence link = bundle.getString("link").trim();
                String time = bundle.getString("time");
                String alert = bundle.getString("alert");
                String id = bundle.getString("id");
                done = bundle.getString("done");

                this.subtitle.setText(title);
                this.location.setText(location);
                this.link.setText(link);

                this.todo_meet_form_add_time_show.setText(alert.trim());
                this.meet_form_hour_picker.setValue(Integer.parseInt(alert.split(":")[0]));
                this.meet_form_minus_picker.setValue(Integer.parseInt(alert.split(":")[1]));
                this.todo_meet_form_year.setValue(Integer.parseInt(time.split(" ")[1].split("/")[2]));
                this.todo_meet_form_month.setValue(Integer.parseInt(time.split(" ")[1].split("/")[1]));
                this.todo_meet_form_day.setValue(Integer.parseInt(time.split(" ")[1].split("/")[0]));
                this.todo_meet_form_minus.setValue(Integer.parseInt(time.split(" ")[0].split(":")[1]));
                this.todo_meet_form_hour.setValue(Integer.parseInt(time.split(" ")[0].split(":")[0]));
                this.setArguments(null);
                meet_form_button_remove.setVisibility(View.VISIBLE);
                meet_form_button_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("func", "remove_meeting");
                        bundle.putString("id", id);
                        fragment_todo.setArguments(bundle);

                        resetForm();

                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragment_todo).commit();
                    }
                });

                final String finalDone = done;
                meet_form_button_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send("edit_info_meeting", id, finalDone);
                    }
                });
            }
        }
        else{
            meet_form_button_remove.setVisibility(View.GONE);
            Calendar now = Calendar.getInstance();
            todo_meet_form_day.setValue(now.get(Calendar.DAY_OF_MONTH));
            todo_meet_form_month.setValue(now.get(Calendar.MONTH) + 1);
            todo_meet_form_year.setValue(now.get(Calendar.YEAR));
            todo_meet_form_hour.setValue(now.get(Calendar.HOUR_OF_DAY));
            todo_meet_form_minus.setValue(now.get(Calendar.MINUTE));

            resetForm();

            meet_form_button_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    send("create_meeting", "", "");
                }
            });

        }
    }

    private void init(View meeting_form){
        todo_meet_form_line_1_title = meeting_form.findViewById(R.id.todo_meet_form_line_1_title);
        todo_meet_form_line_1_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        todo_meet_form_line_5 = meeting_form.findViewById(R.id.todo_meet_form_line_5);
        todo_meet_form_line_5.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        meet_form_close = meeting_form.findViewById(R.id.todo_meet_form_close);

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

        subtitle = meeting_form.findViewById(R.id.meeting_form_sub);//value
        location = meeting_form.findViewById(R.id.meeting_form_local);//value
        link = meeting_form.findViewById(R.id.meeting_form_link);//value

        //select time
        meet_form_hour_picker = meeting_form.findViewById(R.id.meet_form_hour_picker);//value
        meet_form_minus_picker = meeting_form.findViewById(R.id.meet_form_minus_picker);//value
        setMin_Max(meet_form_hour_picker, 0, 23);
        setMin_Max(meet_form_minus_picker, 0, 59);

        meet_form_button_dialog_done = meeting_form.findViewById(R.id.meet_form_button_dialog_done);

        //refresh time init
        todo_meet_form_refresh_time = meeting_form.findViewById(R.id.todo_meet_form_refresh_time);

        //add time init
        todo_meet_form_add_time_show = meeting_form.findViewById(R.id.todo_meet_form_add_time_show);
        todo_meet_form_add_time_dialog = meeting_form.findViewById(R.id.todo_meet_form_add_time_dialog);
        container_dialog = meeting_form.findViewById(R.id.container_dialog);

        meet_form_button_done = meeting_form.findViewById(R.id.meet_form_button_done);
        meet_form_button_remove = meeting_form.findViewById(R.id.meet_form_button_remove);
    }

    private void close_form() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragment_todo).commit();
    }

    private void close_dialog(){
        if(todo_meet_form_add_time_dialog.getVisibility() == View.VISIBLE){
            todo_meet_form_add_time_dialog.setVisibility(View.GONE);
            bottomNavigationView.setForeground(null);
        }
    }

    private void open_dialog(){
        if(todo_meet_form_add_time_dialog.getVisibility() == View.GONE){

            bottomNavigationView.setForeground(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.dialog, null)));
            todo_meet_form_add_time_dialog.getLayoutParams().height = -1;
            todo_meet_form_add_time_dialog.getLayoutParams().width = -1;
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

        return formatTime(hour, minus) + " " + res + Integer.toString(year);
    }// 00:00 dd/mm/yyyy


    private String checkTimeForm(String check){
        Calendar d = Calendar.getInstance();
        String now = d.get(Calendar.HOUR_OF_DAY) + ":" +d.get(Calendar.MINUTE) + " " + d.get(Calendar.DAY_OF_MONTH) + "/" + (d.get(Calendar.MONTH) + 1) + "/" + d.get(Calendar.YEAR);
        int i = check(check, now);
        if(i == 1) return check;
        return now;
    }
    private Boolean checkSubtitleForm(Editable input){
        if(input.toString().trim().equals("")) return false;
        return true;
    }
    private Boolean checkLocationForm(Editable input){
        if(input.toString().trim().equals("")) return false;
        return true;
    }
    private Boolean checkLinkForm(Editable input){
        if(input.toString().trim().equals("")) return false;
        return true;
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

    private void send(String type, String id, String done){
        Editable form_title = subtitle.getText();
        Editable form_local = location.getText();
        Editable form_link = link.getText();
        if(checkSubtitleForm(form_title) == false || checkLinkForm(form_link) == false && checkLocationForm(form_local) == false){
            if(checkSubtitleForm(form_title) == false){
                subtitle.setError("Hãy nhập nội dung cuộc họp");
            }
            if(checkLinkForm(form_link) == false && checkLocationForm(form_local) == false){
                location.setError("Hãy nhập địa điểm/link cuộc họp");
                link.setError("Hãy nhập địa điểm/link cuộc họp");
            }
        }
        else{
            String form_time = formatDate(todo_meet_form_day.getValue(), todo_meet_form_month.getValue(), todo_meet_form_year.getValue(), todo_meet_form_hour.getValue(), todo_meet_form_minus.getValue());
            int form_timer_hour = meet_form_hour_picker.getValue();
            int form_timer_minus = meet_form_minus_picker.getValue();
            String form_alert = formatTime(form_timer_hour, form_timer_minus);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                form_time = checkTimeForm(form_time);
            }
            //todo: create Todo_class

            Bundle bundle = new Bundle();
            bundle.putString("func", type);
            if(!id.equals("")) bundle.putString("id", id);
            bundle.putString("title", form_title.toString());
            bundle.putString("location", form_local.toString());
            bundle.putString("link", form_link.toString());
            bundle.putString("time", form_time);
            bundle.putString("alert", form_alert);
            if(!done.equals("")) bundle.putString("done", done);

            Calendar now = Calendar.getInstance();
            todo_meet_form_day.setValue(now.get(Calendar.DAY_OF_MONTH));
            todo_meet_form_month.setValue(now.get(Calendar.MONTH) + 1);
            todo_meet_form_year.setValue(now.get(Calendar.YEAR));
            todo_meet_form_hour.setValue(now.get(Calendar.HOUR_OF_DAY));
            todo_meet_form_minus.setValue(now.get(Calendar.MINUTE));

            resetForm();

            fragment_todo.setArguments(bundle);
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragment_todo).commit();
        }
    }

    private void resetForm(){
        this.subtitle.setText("");
        this.location.setText("");
        this.link.setText("");
        this.todo_meet_form_add_time_show.setText("00:05");
        this.meet_form_minus_picker.setValue(5);
        this.meet_form_hour_picker.setValue(0);
        this.subtitle.setError(null);
        this.location.setError(null);
        this.link.setError(null);
    }
}
