package com.app.timetable;

import static com.app.timetable.DataBaseHelper.*;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class fragment_new_subject extends Fragment {
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private String className,classRoom,classGroup, note;
    private String startDate = "",endDate = "",startHour = "",endHour = "";
    private boolean[] studyDay = {false,false,false,false,false,false,false};
    private String lecturerName = "", lecturerMail = "", lecturerNumber = "";
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;
    private Button add_info_lecturer_buttn,done_info_lecturer_bttn,done_add_time_study_bttn;
    private TextView day_start,day_end;
    private TextView day_t2,day_t3,day_t4,day_t5,day_t6,day_t7,day_cn;
    private TextView study_time_start,study_time_end;
    private RelativeLayout day_begin_layout, day_end_layout;
    private LinearLayout add_info_lecturer_layout, save_new_subject_layout,back_button;
    private RelativeLayout popup_bg, study_time_selector;
    private EditText edttext_lecturer_name,edttext_lecturer_number,edttext_lecturer_mail;
    private NumberPicker hoursPicker,minutesPicker;
    private EditText edttext_subject_name,edttext_group_subject,edttext_subject_room;
    private DataBaseHelper dataBaseHelper;

    private fragment_calendar fragmentCalendar;

    private AddSubject AddSubjectListener;

    public interface AddSubject {
        void AddSubject(Subject subject);
    }

    private BottomNavigationView bottomNavigationView;
    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override
    public void onStart() {
        super.onStart();
        edttext_subject_room.setText("");
        edttext_group_subject.setText("");
        edttext_subject_name.setText("");
        edttext_lecturer_mail.setText("");
        edttext_lecturer_name.setText("");
        edttext_lecturer_number.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AddSubjectListener = (AddSubject) getActivity();
    }

    public void set_calendar_fragment(fragment_calendar fragment){
        fragmentCalendar = fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bottomNavigationView.setForeground(null);
        View view = inflater.inflate(R.layout.new_subject_layout, container, false);

        dataBaseHelper = new DataBaseHelper(view.getContext());

        hoursPicker = view.findViewById(R.id.calendar_add_hours);
        minutesPicker = view.findViewById(R.id.calendar_add_minutes);

        setMin_Max(hoursPicker,00,23);
        setMin_Max(minutesPicker,00,59);

        back_button = view.findViewById(R.id.back_button);

        edttext_subject_name = view.findViewById(R.id.edttext_subject_name);
        edttext_group_subject = view.findViewById(R.id.edttext_group_subject);
        edttext_subject_room = view.findViewById(R.id.edttext_subject_room);
        edttext_subject_room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_DONE || keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    closeKeyBoard();
                    return true;
                }
                return false;
            }
        });

        save_new_subject_layout = view.findViewById(R.id.save_new_subject);

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
        edttext_lecturer_mail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_DONE || keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    closeKeyBoard();
                    return true;
                }
                return false;
            }
        });
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

        back_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,fragmentCalendar).commit();
           }
       });

        save_new_subject_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                className = edttext_subject_name.getText().toString();
                classGroup = edttext_group_subject.getText().toString();
                classRoom = edttext_subject_room.getText().toString();
                Subject subject = new Subject(className,classRoom,classGroup,"",startDate,endDate,startHour,endHour,studyDay,lecturerName,lecturerNumber,lecturerMail);
                String study = "";
                for(int i = 0; i < subject.getStudyDate().length; i++){
                    if(study.equals(""))
                    {
                        if(subject.getStudyDate()[i])
                            study = "T";
                        else
                            study = "F";
                        continue;
                    }
                    if(subject.getStudyDate()[i])
                        study = study.concat("-T");
                    else
                        study = study.concat("-F");
                }
                subject.setStudy(study);
                boolean success = dataBaseHelper.addOne(subject);
                Log.e("subject", ""+success);
                int subject_id = dataBaseHelper.getNewlyInsertedSubject();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date start, end;
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                try {
                    start = format.parse(startDate);
                    end = format.parse(endDate);
                    for(Date i = start; !start.after(end); i.setTime(i.getTime() + MILLIS_IN_A_DAY))
                    {
                        String date = format.format(i);
                        calendar.setTime(i);
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        if(dayOfWeek == 1)
                        {
                            if(studyDay[6])
                            {
                                TimeTable timeTable = new TimeTable(-1, className, classGroup, classRoom, date, startHour, endHour, lecturerName, lecturerNumber, lecturerMail, true, "0:05", 1, subject_id);
                                success = dataBaseHelper.addOne(timeTable);
                                Log.e("timetable", ""+success);
                            }
                        }
                        else{
                            if(studyDay[dayOfWeek-2])
                            {

                                TimeTable timeTable = new TimeTable(-1, className, classGroup, classRoom, date, startHour, endHour, lecturerName, lecturerNumber, lecturerMail, true, "0:05", 1, subject_id);
                                success = dataBaseHelper.addOne(timeTable);
                                Log.e("timetable", ""+success);
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

//                addSubjectData(subject);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,fragmentCalendar).commit();
//                private String className,classRoom,classGroup, note;
//                private String startDate = "",endDate = "",startHour = "",endHour = "";
//                private boolean[] studyDay = {false,false,false,false,false,false,false};
//                private String lecturerName = "", lecturerMail = "", lecturerNumber = "";
                if (TextUtils.isEmpty(className)) {
                    edttext_subject_name.setError("This can't be empty!!");
                }
                // another field errors haven't implemented

            }
        });

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int year = calendar.get(Calendar.YEAR);

        calendar.set(Calendar.YEAR, year -1 );
        long lastYear = calendar.getTimeInMillis();

        calendar.set(Calendar.YEAR, year + 1);
        long nextYear = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraints = new CalendarConstraints.Builder();
        constraints.setStart(lastYear);
        constraints.setEnd(nextYear);
        constraints.setOpenAt(MaterialDatePicker.todayInUtcMilliseconds());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày bắt đầu")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .setCalendarConstraints(constraints.build());

        MaterialDatePicker<Long> startPicker = builder.build();

        MaterialDatePicker.Builder builder2 = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày bắt đầu")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .setCalendarConstraints(constraints.build());

        MaterialDatePicker<Long> endPicker = builder2.build();

        startPicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Date date = new Date(selection);
                startDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                day_start.setText(startDate);
                Log.e("start", startDate);
            }
        });

        endPicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Date date = new Date(selection);
                endDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                day_end.setText(endDate);
                Log.e("end", endDate);
            }
        });


        study_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                open_study_time_begin();
            }
        });

        study_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                open_study_time_end();
            }
        });

        day_t2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t2.getBackgroundTintMode() == PorterDuff.Mode.MULTIPLY) {
                    studyDay[0] = true;
                    day_t2.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t2.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t2.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[0] = false;
                    day_t2.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t2.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t2.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        day_t3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t3.getBackgroundTintMode() == PorterDuff.Mode.MULTIPLY) {
                    studyDay[1] = true;
                    day_t3.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t3.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t3.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[1] = false;
                    day_t3.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t3.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t3.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        day_t4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t4.getBackgroundTintMode() == PorterDuff.Mode.MULTIPLY) {
                    studyDay[2] = true;
                    day_t4.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t4.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t4.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[2] = false;
                    day_t4.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t4.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t4.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        day_t5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t5.getBackgroundTintMode() == PorterDuff.Mode.MULTIPLY) {
                    studyDay[3] = true;
                    day_t5.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t5.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t5.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[3] = false;
                    day_t5.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t5.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t5.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        day_t6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t6.getBackgroundTintMode() == PorterDuff.Mode.MULTIPLY) {
                    studyDay[4] = true;
                    day_t6.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t6.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t6.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[4] = false;
                    day_t6.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t6.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t6.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        day_t7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_t7.getBackgroundTintMode() == PorterDuff.Mode.MULTIPLY) {
                    studyDay[5] = true;
                    day_t7.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_t7.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t7.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[5] = false;
                    day_t7.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_t7.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_t7.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        day_cn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (day_cn.getBackgroundTintMode() == PorterDuff.Mode.MULTIPLY) {
                    studyDay[6] = true;
                    day_cn.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.choosen_day));
                    day_cn.setTextColor(view.getContext().getResources().getColorStateList(R.color.white));
                    day_cn.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                else {
                    studyDay[6] = false;
//                    day_cn.setBackground(view.getContext().getResources().getDrawable(R.drawable.calendar_shape_background));
                    day_cn.setBackgroundTintList(view.getContext().getResources().getColorStateList(R.color.white));
                    day_cn.setTextColor(view.getContext().getResources().getColorStateList(R.color.black));
                    day_cn.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        day_begin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPicker.show(getActivity().getSupportFragmentManager(), "START_DATE_PICKER");
            }
        });

        day_end_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endPicker.show(getActivity().getSupportFragmentManager(), "END_DATE_PICKER");
            }
        });
        add_info_lecturer_buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_info_lecturer_bg();
            }
        });

        popup_bg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (add_info_lecturer_layout.getVisibility() == View.VISIBLE){
                    close_info_lecturer_bg();
                }
                if (study_time_selector.getVisibility() == View.VISIBLE){
                    close_popup_selector();
                }
            }
        });
        add_info_lecturer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        final Calendar c = Calendar.getInstance();
//        this.lastSelectedYear = c.get(Calendar.YEAR);
//        this.lastSelectedMonth = c.get(Calendar.MONTH);
//        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        return view;
    }

    private void setMin_Max(NumberPicker picker, int min, int max){
        picker.setMinValue(min);
        picker.setMaxValue(max);
        picker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
    }

    private void open_study_time_begin(){
        popup_bg.setVisibility(View.VISIBLE);
        study_time_selector.setVisibility(View.VISIBLE);
        bottomNavigationView.setForeground(new ColorDrawable(Color.parseColor("#CC333333")));
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
                startHour=hour_start+":"+minute_start;
                study_time_start.setText(startHour);
                close_popup_selector();
            }
        });
    }

    private void open_study_time_end(){
        popup_bg.setVisibility(View.VISIBLE);
        study_time_selector.setVisibility(View.VISIBLE);
        bottomNavigationView.setForeground(new ColorDrawable(Color.parseColor("#CC333333")));
        closeKeyBoard();
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
                endHour = hour_start+":"+minute_start;
                study_time_end.setText(endHour);
                close_popup_selector();
            }
        });
    }

    private void close_popup_selector(){
        popup_bg.setVisibility(View.GONE);
        study_time_selector.setVisibility(View.GONE);
        bottomNavigationView.setForeground(null);
        closeKeyBoard();
    }

    private void close_info_lecturer_bg(){
        popup_bg.setVisibility(View.GONE);
        add_info_lecturer_layout.setVisibility(View.GONE);
        bottomNavigationView.setForeground(null);
        closeKeyBoard();
    }

    private void open_info_lecturer_bg(){
        closeKeyBoard();
        edttext_lecturer_number.setText(lecturerNumber);
        edttext_lecturer_name.setText(lecturerName);
        edttext_lecturer_mail.setText(lecturerMail);

        add_info_lecturer_layout.setVisibility(View.VISIBLE);
        popup_bg.setVisibility(View.VISIBLE);

        bottomNavigationView.setForeground(new ColorDrawable(Color.parseColor("#CC333333")));

        done_info_lecturer_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edttext_lecturer_name.getText().toString().trim();
                String number = edttext_lecturer_number.getText().toString().trim();
                String mail = edttext_lecturer_mail.getText().toString().trim();
                Log.d("test",name + " " + number + " " + mail);
                if (!(name.equals("")) || !(number.equals("")) || !(mail.equals(""))){
                    add_info_lecturer_buttn.setText("Sửa");
                    lecturerName = name;
                    lecturerMail = mail;
                    lecturerNumber = number;
                }
                else{
                    add_info_lecturer_buttn.setText("Thêm");
                }
                close_info_lecturer_bg();
            }
        });
    }


//    private void buttonSelectDateBegin(View view) {
//
//        // Date Select Listener.
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year,
//                                  int monthOfYear, int dayOfMonth) {
//                startDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//                day_start.setText(startDate);
//
//                lastSelectedYear = year;
//                lastSelectedMonth = monthOfYear;
//                lastSelectedDayOfMonth = dayOfMonth;
//            }
//        };
//
//        DatePickerDialog datePickerDialog = null;
//
//        datePickerDialog = new DatePickerDialog(view.getContext(),
//                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
////        }
//
//        // Show
//        datePickerDialog.show();
//    }
//    private void buttonSelectDateEnd(View view) {
//
//        // Date Select Listener.
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year,
//                                  int monthOfYear, int dayOfMonth) {
//                endDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//                day_end.setText(endDate);
//
//                lastSelectedYear = year;
//                lastSelectedMonth = monthOfYear;
//                lastSelectedDayOfMonth = dayOfMonth;
//            }
//        };
//
//        DatePickerDialog datePickerDialog = null;
//        datePickerDialog = new DatePickerDialog(view.getContext(),
//                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
//        // Show
//        datePickerDialog.show();
//    }

    private void addSubjectData(Subject subject){
        AddSubjectListener.AddSubject(subject);
    }
    private void closeKeyBoard(){
        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
