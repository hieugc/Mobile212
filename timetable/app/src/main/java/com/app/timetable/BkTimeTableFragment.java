package com.app.timetable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class BkTimeTableFragment extends Fragment {

    private RecyclerView timetableView;
    private DataBaseHelper dataBaseHelper;
    private int userid;
    private fragment_calendar fragmentCalendar;


    public BkTimeTableFragment(int id, fragment_calendar fragmentCalendar) {
        this.userid = id;
        this.fragmentCalendar = fragmentCalendar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View bkTimeTable =  inflater.inflate(R.layout.fragment_bk_time_table, container, false);

        dataBaseHelper = new DataBaseHelper(bkTimeTable.getContext());

        timetableView = bkTimeTable.findViewById(R.id.bk_timetable_recyclerView);


        ArrayList<BKTimeTable> timetable = dataBaseHelper.getTimeTable(userid);

        TimeTableRecViewAdapter adapter = new TimeTableRecViewAdapter(getActivity(), fragmentCalendar);


        adapter.setTimetable(timetable);

        timetableView.setAdapter(adapter);
        timetableView.setLayoutManager(new LinearLayoutManager(bkTimeTable.getContext()));

        return bkTimeTable;
    }
}