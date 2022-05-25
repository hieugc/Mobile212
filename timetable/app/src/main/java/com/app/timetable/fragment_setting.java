package com.app.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class fragment_setting extends Fragment {
    public fragment_setting(){}
    RelativeLayout layout3;
    private CheckBox checkBox;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 =  inflater.inflate(R.layout.fragment_setting, container, false);
        layout3 = view1.findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(),OnBoardingActivity.class);
                startActivity(intent);
            }
        });
        return view1;
    }

}
