package com.app.timetable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class BkTimeTableFragment extends Fragment {

    private RecyclerView timetableView;
    private DataBaseHelper dataBaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View bkTimeTable =  inflater.inflate(R.layout.fragment_bk_time_table, container, false);

        dataBaseHelper = new DataBaseHelper(bkTimeTable.getContext());

        timetableView = bkTimeTable.findViewById(R.id.bk_timetable_recyclerView);


        ArrayList<BKTimeTable> timetable = new ArrayList<>();

        timetable.add(new BKTimeTable("Đồ án đa ngành (CO3011)", "H1-603","7:00 - 8:50", "01|02|03|04|--|--|07|08|09|--|11|12|13|14|15|16|17|18|"));
        timetable.add(new BKTimeTable("Nguyên lý ngôn ngữ lập trình (CO3005)","H6-109","9:00 - 11:50","01|02|03|04|--|--|07|08|09|--|--|--|--|14|15|16|17|18|"));

        TimeTableRecViewAdapter adapter = new TimeTableRecViewAdapter(getActivity());

        adapter.setTimetable(timetable);

        timetableView.setAdapter(adapter);
        timetableView.setLayoutManager(new LinearLayoutManager(bkTimeTable.getContext()));


        return bkTimeTable;
    }
}