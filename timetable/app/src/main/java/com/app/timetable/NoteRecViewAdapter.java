package com.app.timetable;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoteRecViewAdapter extends RecyclerView.Adapter<NoteRecViewAdapter.ViewHolder>{

    private ArrayList<Note> noteArrayList;

    public NoteRecViewAdapter()
    {
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.heading_txt.setText(noteArrayList.get(position).getTitle());
        holder.content_txt.setText(noteArrayList.get(position).getContent());
        String created_at = noteArrayList.get(position).getDate();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(created_at);
            created_at = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.time_txt.setText(created_at);
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), "Item Clicked: "+position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public void setNoteArrayList(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView heading_txt, content_txt, time_txt;
        private RelativeLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading_txt = itemView.findViewById(R.id.note_txt);
            content_txt = itemView.findViewById(R.id.note_content);
            time_txt = itemView.findViewById(R.id.note_time_txt);
            parent = itemView.findViewById(R.id.note_item);

        }
    }
}
