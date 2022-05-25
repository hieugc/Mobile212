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

public class fragment_calendar_info_subject extends Fragment {

    private TextView txtView_info_subject_name,txtView_info_subject_date;
    private TextView txtView_group_subject_id,txtView_info_study_room,txtView_info_study_time;
    private TextView txtView_info_lecturer_name, txtView_info_lecturer_number,txtView_info_lecturer_mail;
    private String subjectName,subject_group,study_room,startHour,endHour,lecturerName,lecturerNumber,lecturerMail;
    private LinearLayout back_button;

    private fragment_calendar fragmentCalendar;
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

        txtView_info_subject_name.setText(subjectName);
        txtView_group_subject_id.setText("Nhóm - Tổ: " +subject_group);
        txtView_info_study_room.setText("Phòng học: " +study_room);
        // startHour,endHour,lecturerName,lecturerNumber,lecturerMail;
        txtView_info_study_time.setText("Giờ học: " + startHour + " - " + endHour);
        txtView_info_lecturer_name.setText("Họ và tên: " +lecturerName);
        txtView_info_lecturer_number.setText("Số điện thoại: " + lecturerNumber);
        txtView_info_lecturer_mail.setText("Email: " + lecturerMail);

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
