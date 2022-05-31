package com.app.timetable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AboutFragment extends Fragment {
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        imageView = view.findViewById(R.id.back_to_settings);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_setting fragment_setting = new fragment_setting();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragment_setting).commit();
            }
        });

        return view;
    }
}