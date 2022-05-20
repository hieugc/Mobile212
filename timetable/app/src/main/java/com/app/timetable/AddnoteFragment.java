package com.app.timetable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class AddnoteFragment extends Fragment {
    ImageButton button1;
    fragment_note note1;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addnote, container, false);
        button1 = view.findViewById(R.id.imageButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                note1 = new fragment_note();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,note1).commit();
            }
        });
        return view;
    }
}