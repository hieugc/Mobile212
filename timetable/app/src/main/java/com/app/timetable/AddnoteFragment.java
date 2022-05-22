package com.app.timetable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddnoteFragment extends Fragment {
    ImageButton button1;
    fragment_note note1;
    fragment_setting note2;
    EditText input1,input2;
    MaterialButton button2;
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
        input1 = view.findViewById(R.id.name_input);
        input2 = view.findViewById(R.id.name_input1);
        button2 = view.findViewById(R.id.button2);

        Realm.init(getActivity().getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = input1.getText().toString();
                String content = input2.getText().toString();

                realm.beginTransaction();
                Note note = realm.createObject(Note.class);
                note.setTitle(title);
                note.setContent(content);
                realm.commitTransaction();
                Toast.makeText(getActivity().getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
                note1 = new fragment_note();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,note1).commit();
//                getActivity().finish();
            }
        });
        return view;
    }
}