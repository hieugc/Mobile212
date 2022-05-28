package com.app.timetable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class fragment_note extends Fragment {
    public fragment_note(){}

    private AddnoteFragment addnoteFragment;
    private FloatingActionButton add_btn;
    private RecyclerView note_recycleView;
    private DataBaseHelper dataBaseHelper;
    private Context context;
    private NoteRecViewAdapter.ViewHolder viewHolder;
    private TextView heading_txt, sub_heading_txt, cancel_txt, noData_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View note_view = inflater.inflate(R.layout.fragment_note, container, false);
        context = note_view.getContext();
        add_btn = note_view.findViewById(R.id.btn_add_note);
        note_recycleView = note_view.findViewById(R.id.note_recycleView);
        note_recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sub_heading_txt = note_view.findViewById(R.id.note_heading_description);
        cancel_txt = note_view.findViewById(R.id.cancel_note);
        heading_txt = note_view.findViewById(R.id.note_heading_txt);
        noData_txt = note_view.findViewById(R.id.note_noData_txt);

        dataBaseHelper = new DataBaseHelper(note_view.getContext());


        NoteRecViewAdapter adapter = new NoteRecViewAdapter(dataBaseHelper, this);

        setItemTouchHelper();
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

    private void setItemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.Callback() {

            private int limitScrollX = dipToPx(65f, context);
            private int currentScrollX = 0;
            private int currentScrollXWhenInActive = 0;
            private float initXWhenInActive = 0f;
            private boolean firstInActive = false;


            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public float getSwipeEscapeVelocity(float defaultValue) {
                return (float) Integer.MAX_VALUE;
            }

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return (float) Integer.MAX_VALUE;

            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);

                if(viewHolder.itemView.getScrollX() > limitScrollX)
                {
                    viewHolder.itemView.scrollTo(limitScrollX, 0);
                }
                else if(viewHolder.itemView.getScrollX() < 0)
                {
                    viewHolder.itemView.scrollTo(0, 0);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
                {
                    if(dX == 0f)
                    {
                        currentScrollX = viewHolder.itemView.getScrollX();
                        firstInActive = true;
                    }

                    if(isCurrentlyActive)
                    {
                        int scrollOffset = currentScrollX + (int) (-dX);
                        if(scrollOffset > limitScrollX)
                        {
                            scrollOffset = limitScrollX;
                        }
                        else if(scrollOffset < 0)
                        {
                            scrollOffset = 0;
                        }

                        viewHolder.itemView.scrollTo(scrollOffset, 0);
                    }
                    else{
                        if(firstInActive)
                        {
                            firstInActive = false;
                            currentScrollXWhenInActive = viewHolder.itemView.getScrollX();
                            initXWhenInActive = dX;
                        }
                        if(viewHolder.itemView.getScrollX() < limitScrollX)
                        {
                            viewHolder.itemView.scrollTo((int)(currentScrollXWhenInActive* dX/ initXWhenInActive) , 0);
                        }
                    }
                }
            }

        }).attachToRecyclerView(note_recycleView);
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
        sub_heading_txt.setVisibility(View.INVISIBLE);
        cancel_txt.setVisibility(View.VISIBLE);
        heading_txt.setText(R.string.delete_note);
    }

    public void setCancelView()
    {
        sub_heading_txt.setVisibility(View.VISIBLE);
        cancel_txt.setVisibility(View.GONE);
        heading_txt.setText(R.string.note);
        viewHolder.getDelete_box().setVisibility(View.GONE);
    }

    public int dipToPx(float dp, Context context)
    {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
