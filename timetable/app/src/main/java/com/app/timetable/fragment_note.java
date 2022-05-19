package com.app.timetable;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;

public class fragment_note extends Fragment {
    public fragment_note(){}
    AddnoteFragment addnote;
    FloatingActionButton button1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view1 = inflater.inflate(R.layout.fragment_note, container, false);
          button1 = view1.findViewById(R.id.note_add);
          button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addnote = new AddnoteFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,addnote).commit();
            }
        });


        return view1;
    }
}
