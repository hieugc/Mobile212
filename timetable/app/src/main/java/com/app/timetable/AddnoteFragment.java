package com.app.timetable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddnoteFragment extends Fragment {
    private ImageButton back_btn;
    private fragment_note fragmentNote;
    private EditText title_txt, content_txt;
    private TextView done_txt;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addnote, container, false);


        back_btn = view.findViewById(R.id.back_btn_note);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fragmentNote = new fragment_note();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragmentNote).commit();
            }
        });


        title_txt = view.findViewById(R.id.note_heading_editText);
        content_txt = view.findViewById(R.id.note_content_editText);
        done_txt = view.findViewById(R.id.done_txt);

        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = title_txt.getText().toString();
                String content = content_txt.getText().toString();

                Note note = new Note(-1, title, content);
                Toast.makeText(view.getContext(), note.toString(), Toast.LENGTH_SHORT).show();
                fragmentNote = new fragment_note();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,fragmentNote).commit();
//                getActivity().finish();
            }
        });
        return view;
    }
}