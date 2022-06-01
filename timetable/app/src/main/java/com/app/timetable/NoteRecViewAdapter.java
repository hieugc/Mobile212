package com.app.timetable;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoteRecViewAdapter extends RecyclerView.Adapter<NoteRecViewAdapter.ViewHolder>{

    private ArrayList<Note> noteArrayList;
    private DataBaseHelper dataBaseHelper;
    private fragment_note fragmentNote;

    public NoteRecViewAdapter(DataBaseHelper dataBaseHelper, fragment_note fragmentNote) {
        this.dataBaseHelper = dataBaseHelper;
        this.fragmentNote = fragmentNote;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.heading_txt.setText(noteArrayList.get(position).getTitle());
        if (noteArrayList.get(position).getContent().length() > 40){
            String txt = noteArrayList.get(position).getContent().substring(0, 39) + "...";
            holder.content_txt.setText(txt);
        }
        else{
            holder.content_txt.setText(noteArrayList.get(position).getContent());
        }
        String created_at = noteArrayList.get(position).getDate();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(created_at);
            created_at = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.time_txt.setText(created_at);

        holder.delete_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext(), R.style.RoundShapeTheme);
                builder.setTitle("Xác nhận xoá ghi chú?");
                builder.setMessage("Bạn muốn xoá ghi chú này chứ?");
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.itemView.scrollTo(0, 0);
                        Toast.makeText(view.getContext(), "Huỷ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.itemView.scrollTo(0, 0);
                        boolean success = dataBaseHelper.deleteOne(noteArrayList.get(position));
                        if(success){
                            setNoteArrayList(dataBaseHelper.getAllNote());
                            if(getItemCount() == 0) fragmentNote.getNoData_txt().setVisibility(View.VISIBLE);
                            else fragmentNote.getNoData_txt().setVisibility(View.GONE);
                            fragmentNote.setCancelView();
                        }
                    }
                });
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        holder.itemView.scrollTo(0, 0);
                        Log.e("dismiss", "dialog");
                    }
                });
                builder.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("func", "openInfoNote");
                bundle.putParcelable("note", noteArrayList.get(position));

                AddnoteFragment addnoteFragment = new AddnoteFragment();
                addnoteFragment.setArguments(bundle);

                fragmentNote.getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, addnoteFragment).commit();
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
        private RelativeLayout delete_box;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading_txt = itemView.findViewById(R.id.note_txt);
            content_txt = itemView.findViewById(R.id.note_content);
            time_txt = itemView.findViewById(R.id.note_time_txt);
            delete_box = itemView.findViewById(R.id.delete_note);
        }

    }
}
