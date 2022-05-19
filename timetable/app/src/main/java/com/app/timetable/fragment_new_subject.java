package com.app.timetable;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class fragment_new_subject extends Fragment {
    private String className,classRoom,classGroup, note;
    private String startDate,endDate,startHour,endHour;
    private boolean[] studyDay = {false,false,false,false,false,false,false};
    private String lecturerName = "", lecturerMail = "", lecturerNumber = "";
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;
    private Button add_info_lecturer_buttn,done_info_lecturer_bttn,done_add_time_study_bttn;
    private TextView day_start,day_end;
    private TextView day_t2,day_t3,day_t4,day_t5,day_t6,day_t7,day_cn;
    private TextView study_time_start,study_time_end;
    private FrameLayout day_begin_layout, day_end_layout;
    private LinearLayout add_info_lecturer_layout;
    private RelativeLayout popup_bg, study_time_selector;
    private EditText edttext_lecturer_name,edttext_lecturer_number,edttext_lecturer_mail;
    private NumberPicker hoursPicker,minutesPicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_subject_layout, container, false);

        hoursPicker = view.findViewById(R.id.calendar_add_hours);
        minutesPicker = view.findViewById(R.id.calendar_add_minutes);

        setMin_Max(hoursPicker,00,23);
        setMin_Max(minutesPicker,00,59);

        study_time_start = view.findViewById(R.id.study_time_start);
        study_time_end = view.findViewById(R.id.study_time_end);
        study_time_selector = view.findViewById(R.id.study_time_selector);
        done_add_time_study_bttn = view.findViewById(R.id.done_add_time_study);

        hoursPicker.setValue(07);
        minutesPicker.setValue(00);
        day_start = view.findViewById(R.id.subject_day_begin);
        day_end = view.findViewById(R.id.subject_day_end);
        day_begin_layout = view.findViewById(R.id.subject_day_begin_layout);
        day_end_layout = view.findViewById(R.id.subject_day_end_layout);
        add_info_lecturer_layout = view.findViewById(R.id.add_lecturer_info_layout);
        popup_bg = view.findViewById(R.id.popup_background);
        done_info_lecturer_bttn = view.findViewById(R.id.done_add_info_lecturer);
        edttext_lecturer_mail = view.findViewById(R.id.edttext_email_lecturer);
        edttext_lecturer_name = view.findViewById(R.id.edttext_name_lecturer);
        edttext_lecturer_number = view.findViewById(R.id.edttext_number_lecturer);
        day_t2 = view.findViewById(R.id.day_t2);
        day_t3 = view.findViewById(R.id.day_t3);
        day_t4 = view.findViewById(R.id.day_t4);
        day_t5 = view.findViewById(R.id.day_t5);
        day_t6 = view.findViewById(R.id.day_t6);
        day_t7 = view.findViewById(R.id.day_t7);
        day_cn = view.findViewById(R.id.day_cn);
        day_cn.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));

        add_info_lecturer_buttn = view.findViewById(R.id.add_lecturer_info_button);

        study_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                open_add_study_time(view);
            }
        });
        day_t2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t2.getBackgroundTintMode() == PorterDuff.Mode.SRC_IN) {
                    studyDay[0] = true;
                    day_t2.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t2.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t2.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[0] = false;
                    day_t2.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));
                    day_t2.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t2.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t2.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                }
            }
        });

        day_t3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t3.getBackgroundTintMode() == PorterDuff.Mode.SRC_IN) {
                    studyDay[1] = true;
                    day_t3.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t3.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t3.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[1] = false;
                    day_t3.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));
                    day_t3.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t3.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t3.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                }
            }
        });

        day_t4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t4.getBackgroundTintMode() == PorterDuff.Mode.SRC_IN) {
                    studyDay[2] = true;
                    day_t4.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t4.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t4.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[2] = false;
                    day_t4.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));
                    day_t4.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t4.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t4.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                }
            }
        });

        day_t5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t5.getBackgroundTintMode() == PorterDuff.Mode.SRC_IN) {
                    studyDay[3] = true;
                    day_t5.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t5.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t5.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[3] = false;
                    day_t5.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));
                    day_t5.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t5.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t5.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                }
            }
        });

        day_t6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t6.getBackgroundTintMode() == PorterDuff.Mode.SRC_IN) {
                    studyDay[4] = true;
                    day_t6.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t6.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t6.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[4] = false;
//                    day_t6.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));
                    day_t6.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t6.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t6.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                }
            }
        });

        day_t7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t7.getBackgroundTintMode() == PorterDuff.Mode.SRC_IN) {
                    studyDay[5] = true;
                    day_t7.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t7.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t7.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[5] = false;
                    day_t7.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));
                    day_t7.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t7.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t7.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                }
            }
        });

        day_cn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_cn.getBackgroundTintMode() == PorterDuff.Mode.SRC_IN) {
                    studyDay[6] = true;
                    day_cn.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_cn.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_cn.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[6] = false;
                    day_cn.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));
//                    day_cn.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
//                    day_cn.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_cn.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                }
            }
        });

        day_begin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSelectDateBegin(view);
            }
        });

        day_end_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSelectDateEnd(view);
            }
        });
        add_info_lecturer_buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_info_lecturer_bg(view);
            }
        });

        popup_bg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (add_info_lecturer_layout.getBackgroundTintMode() == PorterDuff.Mode.SRC_OVER){
                    close_info_lecturer_bg(view);
                }
                if (study_time_selector.getBackgroundTintMode() == PorterDuff.Mode.SRC_OVER){
                    close_add_study_time(view);
                }
            }
        });
        add_info_lecturer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        return view;
    }

    private void setMin_Max(NumberPicker picker, int min, int max){
        picker.setMinValue(min);
        picker.setMaxValue(max);
    }

    private void open_add_study_time(View view){
        popup_bg.setVisibility(View.VISIBLE);
        popup_bg.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
        study_time_selector.setVisibility(View.VISIBLE);
        study_time_selector.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
        done_add_time_study_bttn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String hour_start = hoursPicker.getValue()+"";
                String minute_start = minutesPicker.getValue()+"";
                if (hour_start.length() == 1) {
                    hour_start = "0" + hour_start;
                }
                if (minute_start.length() == 1){
                    minute_start = "0" + minute_start;
                }
                study_time_start.setText(hour_start+":"+minute_start);
                close_add_study_time(view);
            }
        });
    }

    private void close_add_study_time(View view){
        popup_bg.setVisibility(View.INVISIBLE);
        popup_bg.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
        study_time_selector.setVisibility(View.INVISIBLE);
        study_time_selector.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }

    private void open_info_lecturer_bg(View view){
        edttext_lecturer_number.setText(lecturerNumber);
        edttext_lecturer_name.setText(lecturerName);
        edttext_lecturer_mail.setText(lecturerMail);
        add_info_lecturer_layout.setVisibility(View.VISIBLE);
        add_info_lecturer_layout.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
        popup_bg.setVisibility(View.VISIBLE);
        popup_bg.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
        done_info_lecturer_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edttext_lecturer_name.getText().toString();
                String number = edttext_lecturer_number.getText().toString();
                String mail = edttext_lecturer_mail.getText().toString();
                if (name != "" || number != ""  || mail != ""){
                    add_info_lecturer_buttn.setText("Sửa");
                    lecturerName = name;
                    lecturerMail = mail;
                    lecturerNumber = number;
                }
                else{
                    add_info_lecturer_buttn.setText("Thêm");
                }
                close_info_lecturer_bg(view);

            }
        });
    }

    private void close_info_lecturer_bg(View view){
        popup_bg.setVisibility(View.INVISIBLE);
        popup_bg.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
        add_info_lecturer_layout.setVisibility(View.INVISIBLE);
        add_info_lecturer_layout.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }

    private void buttonSelectDateBegin(View view) {

        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                day_start.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(view.getContext(),
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
//        }

        // Show
        datePickerDialog.show();
    }
    private void buttonSelectDateEnd(View view) {

        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                day_end.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(view.getContext(),
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        // Show
        datePickerDialog.show();
    }
}
