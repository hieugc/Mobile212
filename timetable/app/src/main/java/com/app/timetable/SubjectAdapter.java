package com.app.timetable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter <SubjectAdapter.ViewHolder>{
    private ArrayList<Subject> subjectList;
    private Context context;
    private int colors[] = {R.color.first_item,R.color.sec_item,R.color.third_item,R.color.fourth_item,R.color.fifth_item,R.color.sixth_item};
    private String colors2[] = {"#FFD5677B","#FA8231","#3867D6","#20BF6B","#0FB9B1","#802D98DA"};
    private fragment_calendar_info_subject info_subject;

    private ISendDataListener mISendDataListener;

    public void setSubjectList(ArrayList<Subject> subjectList) {
        this.subjectList = subjectList;
        this.notifyDataSetChanged();
    }

    public ArrayList<Subject> getSubjectList() {
        return subjectList;
    }

    public interface ISendDataListener{
        void sendData(Subject subject);
    }
    public SubjectAdapter(ArrayList<Subject> subjectList, Context context){
        this.subjectList = subjectList;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private View itemview;
        public TextView subjectname,starthour,endhour, note;
        public MaterialCardView layout;
        private ItemClickListener itemClickListener;
        public ViewHolder(View itemView){
            super(itemView);

            itemview = itemView;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            layout = itemView.findViewById(R.id.subject_layout);
            subjectname = itemView.findViewById(R.id.subject_name);
            starthour = itemView.findViewById(R.id.start_time);
            endhour = itemView.findViewById(R.id.end_time);
            note = itemView.findViewById(R.id.subject_note);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

//        @Override
//        public void onClick(View view){
//            String
//        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(viewType,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (subjectList.size() == 0) {
            Log.d("check layout","blank");
            return R.layout.blank_item_tkb;
        }
        else {
            Log.d("check layout","subject_item");
            return R.layout.subject_item;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (subjectList.size() > 0){
            Subject subject = (Subject) subjectList.get(position);
            if (position == 0){
                holder.layout.setBackground(context.getResources().getDrawable(R.drawable.first_item_style));
                holder.layout.setBackgroundTintList(context.getResources().getColorStateList(colors[position % 6]));
            }
            else if (position == subjectList.size()-1){
                holder.layout.setBackground(context.getResources().getDrawable(R.drawable.last_item_style));
                holder.layout.setBackgroundTintList(context.getResources().getColorStateList(colors[position % 6]));
            }
            else {
                holder.layout.setBackgroundColor(Color.parseColor(colors2[position % 6]));
            }
            holder.subjectname.setText(subject.getClassName());
            holder.starthour.setText(subject.getStartHour());
            holder.endhour.setText(subject.getEndHour());
            holder.note.setText(subject.getNote());
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if(isLongClick)
                        Toast.makeText(context, "Long Click: "+subjectList.get(position), Toast.LENGTH_SHORT).show();
                    else {
                        //((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, info_subject).commit();
//                    Toast.makeText(context, " " + subjectList.get(position), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (subjectList == null || subjectList.isEmpty() || subjectList.size() == 0) {
            return 1;
        } else {
            return subjectList.size();
        }
    }
}
