package com.app.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;

public class fragment_setting extends Fragment {
    public fragment_setting(){}
    private CheckBox checkBox;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 =  inflater.inflate(R.layout.fragment_setting, container, false);
        return view1;
    }

}
