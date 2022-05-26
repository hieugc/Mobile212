package com.app.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class fragment_calendar_info_subject extends Fragment {

    private TextView txtView_info_subject_name,txtView_info_subject_date;
    private TextView txtView_group_subject_id,txtView_info_study_room,txtView_info_study_time;
    private TextView txtView_info_lecturer_name, txtView_info_lecturer_number,txtView_info_lecturer_mail;
    private String subjectName,subject_group,study_room,startHour,endHour,lecturerName,lecturerNumber,lecturerMail;
    private LinearLayout back_button;
    private TimeTable timeTable;
    private fragment_calendar fragmentCalendar;

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public void set_calendar(fragment_calendar fragmentCalendar){
        this.fragmentCalendar = fragmentCalendar;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View subjectInfoView = inflater.inflate(R.layout.subject_info_layout, container, false);
        back_button = subjectInfoView.findViewById(R.id.back_button);
        txtView_info_subject_name = subjectInfoView.findViewById(R.id.info_subject_name);
        txtView_info_subject_date = subjectInfoView.findViewById(R.id.info_subject_date);
        txtView_group_subject_id = subjectInfoView.findViewById(R.id.group_subject_id);
        txtView_info_study_room = subjectInfoView.findViewById(R.id.info_study_room);
        txtView_info_study_time = subjectInfoView.findViewById(R.id.info_study_time);
        txtView_info_lecturer_name = subjectInfoView.findViewById(R.id.info_lecturer_name);
        txtView_info_lecturer_number = subjectInfoView.findViewById(R.id.info_lecturer_number);
        txtView_info_lecturer_mail = subjectInfoView.findViewById(R.id.info_lecturer_mail);

        txtView_info_subject_name.setText(timeTable.getName());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = format.parse(timeTable.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        String dayOfWeek;
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i)
        {
            case 1:
                dayOfWeek = "Chủ Nhật";
                break;
            case 2:
                dayOfWeek = "Thứ 2";
                break;
            case 3:
                dayOfWeek = "Thứ 3";
                break;
            case 4:
                dayOfWeek = "Thứ 4";
                break;
            case 5:
                dayOfWeek = "Thứ 5";
                break;
            case 6:
                dayOfWeek = "Thứ 6";
                break;
            case 7:
                dayOfWeek = "Thứ 7";
                break;
            default:
                dayOfWeek = "Thứ --";
        }

        txtView_info_subject_date.setText(dayOfWeek + " - Ngày "+timeTable.getDate());
        txtView_group_subject_id.setText("Nhóm - Tổ: " +timeTable.getGroup());
        txtView_info_study_room.setText("Phòng học: " +timeTable.getLocation());
        txtView_info_study_time.setText("Giờ học: " + timeTable.getStart_time() + " - " + timeTable.getEnd_time());
        String TA_name, TA_number, TA_email;
        if(timeTable.getTA_name().equals(""))
        {
            TA_name = "Không";
        }
        else TA_name = timeTable.getTA_name();
        if(timeTable.getTA_email().equals(""))
        {
            TA_number = "Không";
        }
        else TA_number = timeTable.getTA_number();
        if(timeTable.getTA_email().equals(""))
        {
            TA_email = "Không";
        }
        else TA_email = timeTable.getTA_email();
        txtView_info_lecturer_name.setText("Họ và tên: " +TA_name);
        txtView_info_lecturer_number.setText("Số điện thoại: " + TA_number);
        txtView_info_lecturer_mail.setText("Email: " + TA_email);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,fragmentCalendar).commit();
            }
        });
        return subjectInfoView;
    }

    public void receiveDataFromCalendarFragment(Subject subject){
        subjectName = subject.getClassName();
        subject_group = subject.getClassGroup();
        study_room = subject.getClassRoom();
        startHour = subject.getStartHour();
        endHour = subject.getEndHour();
        lecturerName = subject.getLecturerName();
        lecturerNumber = subject.getLecturerNumber();
        lecturerMail = subject.getLecturerMail();
    }

}
