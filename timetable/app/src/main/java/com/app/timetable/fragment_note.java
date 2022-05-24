package com.app.timetable;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private DataBaseHelper dataBaseHelper;
    private NoteRecViewAdapter.ViewHolder viewHolder;
    private TextView heading_txt, sub_heading_txt, cancel_txt, noData_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View note_view = inflater.inflate(R.layout.fragment_note, container, false);
        add_btn = note_view.findViewById(R.id.btn_add_note);
        note_recycleView = note_view.findViewById(R.id.note_recycleView);
        note_recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sub_heading_txt = note_view.findViewById(R.id.note_heading_description);
        cancel_txt = note_view.findViewById(R.id.cancel_note);
        heading_txt = note_view.findViewById(R.id.note_heading_txt);
        noData_txt = note_view.findViewById(R.id.note_noData_txt);

        dataBaseHelper = new DataBaseHelper(note_view.getContext());


        NoteRecViewAdapter adapter = new NoteRecViewAdapter(dataBaseHelper, this);
        ArrayList<Note> notes = dataBaseHelper.getAllNote();

        adapter.setNoteArrayList(notes);

        setNoData(adapter);

        adapter.setListener(new NoteRecViewAdapter.OnLongClickListener() {
            @Override
            public boolean onLongClick(NoteRecViewAdapter.ViewHolder holder) {
                setDeleteView();
                viewHolder = holder;
                return false;
            }
        });

        note_recycleView.setAdapter(adapter);
        note_recycleView.setLayoutManager(new LinearLayoutManager(note_view.getContext()));

        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCancelView();
            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addnoteFragment = new AddnoteFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,addnoteFragment).addToBackStack(null).commit();
            }
        });

        return note_view;
    }

    public void setNoData(NoteRecViewAdapter adapter)
    {
        if(adapter.getItemCount() == 0) noData_txt.setVisibility(View.VISIBLE);
        else noData_txt.setVisibility(View.GONE);
    }

    public TextView getNoData_txt() {
        return noData_txt;
    }

    public void setDeleteView()
    {
        sub_heading_txt.setVisibility(View.GONE);
        cancel_txt.setVisibility(View.VISIBLE);
        heading_txt.setText(R.string.delete_note);
    }

    public void setCancelView()
    {
        sub_heading_txt.setVisibility(View.VISIBLE);
        cancel_txt.setVisibility(View.GONE);
        heading_txt.setText(R.string.note);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.getTime_txt().getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        params.setMarginEnd(0);
        viewHolder.getDelete_box().setVisibility(View.GONE);
    }
}
