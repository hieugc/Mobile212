package com.app.timetable;

import android.app.DatePickerDialog;
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
    private Button add_info_lecturer_buttn,done_info_lecturer_bttn;
    private TextView day_start,day_end;
    private FrameLayout day_begin_layout, day_end_layout;
    private LinearLayout add_info_lecturer_layout;
    private RelativeLayout add_info_lecturer_bg;
    private EditText edttext_lecturer_name,edttext_lecturer_number,edttext_lecturer_mail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_subject_layout, container, false);

        day_start = view.findViewById(R.id.subject_day_begin);
        day_end = view.findViewById(R.id.subject_day_end);
        day_begin_layout = view.findViewById(R.id.subject_day_begin_layout);
        day_end_layout = view.findViewById(R.id.subject_day_end_layout);
        add_info_lecturer_layout = view.findViewById(R.id.add_lecturer_info_layout);
        add_info_lecturer_bg = view.findViewById(R.id.add_lecturer_info_background);
        done_info_lecturer_bttn = view.findViewById(R.id.done_add_info_lecturer);
        edttext_lecturer_mail = view.findViewById(R.id.edttext_email_lecturer);
        edttext_lecturer_name = view.findViewById(R.id.edttext_name_lecturer);
        edttext_lecturer_number = view.findViewById(R.id.edttext_number_lecturer);

        add_info_lecturer_buttn = view.findViewById(R.id.add_lecturer_info_button);


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

        add_info_lecturer_bg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                close_info_lecturer_bg(view);
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

    private void open_info_lecturer_bg(View view){
        edttext_lecturer_number.setText(lecturerNumber);
        edttext_lecturer_name.setText(lecturerName);
        edttext_lecturer_mail.setText(lecturerMail);
        add_info_lecturer_layout.setVisibility(View.VISIBLE);
        add_info_lecturer_layout.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
        add_info_lecturer_bg.setVisibility(View.VISIBLE);
        add_info_lecturer_bg.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
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
        add_info_lecturer_bg.setVisibility(View.INVISIBLE);
        add_info_lecturer_bg.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
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
