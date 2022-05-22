package com.app.timetable;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.prefs.PreferenceChangeListener;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class fragment_note extends Fragment {
    public fragment_note(){}
    AddnoteFragment addnote;
    FloatingActionButton button1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view1 = inflater.inflate(R.layout.fragment_note, container, false);
          button1 = view1.findViewById(R.id.note_add);
        Realm.init(getActivity().getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Note> notesList = realm.where(Note.class).findAll();

        RecyclerView recyclerView = view1.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(),notesList);
        recyclerView.setAdapter(myAdapter);
        notesList.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
            @Override
            public void onChange(RealmResults<Note> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });

          button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addnote = new AddnoteFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,addnote).addToBackStack(null).commit();
            }
        });

        return view1;
    }
}
