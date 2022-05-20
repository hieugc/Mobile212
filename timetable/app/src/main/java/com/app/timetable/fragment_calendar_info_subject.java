package com.app.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_calendar_info_subject extends Fragment {

    private TextView txtView_info_subject_name,txtView_info_subject_date;
    private TextView txtView_group_subject_id,txtView_info_study_room,txtView_info_study_time;
    private TextView txtView_info_lecturer_name, txtView_info_lecturer_number,txtView_info_lecturer_mail;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View subjectInfoView = inflater.inflate(R.layout.subject_info_layout, container, false);
        txtView_info_subject_name = subjectInfoView.findViewById(R.id.info_subject_name);
        txtView_info_subject_date = subjectInfoView.findViewById(R.id.info_subject_date);
        txtView_group_subject_id = subjectInfoView.findViewById(R.id.group_subject_id);
        txtView_info_study_room = subjectInfoView.findViewById(R.id.info_study_room);
        txtView_info_study_time = subjectInfoView.findViewById(R.id.info_study_time);
        txtView_info_lecturer_name = subjectInfoView.findViewById(R.id.info_lecturer_name);
        txtView_info_lecturer_number = subjectInfoView.findViewById(R.id.info_lecturer_number);
        txtView_info_lecturer_mail = subjectInfoView.findViewById(R.id.info_lecturer_mail);

        return subjectInfoView;
    }

}
