package com.app.timetable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class fragment_note extends Fragment {
    public fragment_note(){}

    private AddnoteFragment addnoteFragment;
    private FloatingActionButton add_btn;
    private RecyclerView note_recycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View note_view = inflater.inflate(R.layout.fragment_note, container, false);
        add_btn = note_view.findViewById(R.id.btn_add_note);
        note_recycleView = note_view.findViewById(R.id.note_recycleView);
        note_recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        NoteRecViewAdapter adapter = new NoteRecViewAdapter();
        ArrayList<Note> notes = new ArrayList<>();

        notes.add(new Note(-1, "Lời nhắc 1","abcdef"));
        notes.add(new Note(-1, "Lời nhắc 2","abcdef"));

        adapter.setNoteArrayList(notes);

        note_recycleView.setAdapter(adapter);
        note_recycleView.setLayoutManager(new LinearLayoutManager(note_view.getContext()));


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addnoteFragment = new AddnoteFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,addnoteFragment).addToBackStack(null).commit();
            }
        });

        return note_view;
    }
}
