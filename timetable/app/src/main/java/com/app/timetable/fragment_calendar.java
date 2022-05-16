package com.app.timetable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class fragment_calendar extends Fragment {
    public fragment_calendar(){}

    private Button btn_goto;
    private BkTimeTableFragment bkTimeTableFragment = new BkTimeTableFragment();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        btn_goto = view.findViewById(R.id.btn_goto);

        btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, bkTimeTableFragment).commit();
            }
        });

        return view;
    }

}
