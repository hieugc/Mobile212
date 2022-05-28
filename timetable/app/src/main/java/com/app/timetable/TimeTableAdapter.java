package com.app.timetable;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {

    private ArrayList<TimeTable> arrayList = new ArrayList<>();
    private int colors[] = {R.color.first_item,R.color.sec_item,R.color.third_item,R.color.fourth_item,R.color.fifth_item,R.color.sixth_item};
    private String colors2[] = {"#FFD5677B","#FA8231","#3867D6","#20BF6B","#0FB9B1","#802D98DA"};
    private Context context;
    private fragment_calendar fragmentCalendar;
    private fragment_calendar_info_subject info_subject;
    private FragmentActivity fragmentActivity;
    private BottomNavigationView bottomNavigationView;
    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }


    public interface onItemClick{
        public void onClick(TimeTable timeTable);
        public void onDelete(TimeTable timeTable);
    }

    private onItemClick itemClick;

    public TimeTableAdapter(FragmentActivity fragmentActivity, Context context, fragment_calendar fragmentCalendar, onItemClick itemClick) {
        this.fragmentActivity = fragmentActivity;
        this.fragmentCalendar = fragmentCalendar;
        this.context = context;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.startTime_txt.setText(arrayList.get(position).getStart_time());
        holder.endTime_txt.setText(arrayList.get(position).getEnd_time());
        holder.name_txt.setText(arrayList.get(position).getName());
        holder.location_txt.setText(arrayList.get(position).getLocation());
        if (position == 0){
            holder.cardView.setBackground(context.getResources().getDrawable(R.drawable.first_item_style));
            holder.cardView.setBackgroundTintList(context.getResources().getColorStateList(colors[position % 6]));
        }
        else if (position == arrayList.size()-1){
            holder.cardView.setBackground(context.getResources().getDrawable(R.drawable.last_item_style));
            holder.cardView.setBackgroundTintList(context.getResources().getColorStateList(colors[position % 6]));
        }
        else {
            holder.cardView.setBackgroundColor(Color.parseColor(colors2[position % 6]));
        }
        holder.timetableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_subject = new fragment_calendar_info_subject();
                info_subject.setTimeTable(arrayList.get(position));
                info_subject.set_calendar(fragmentCalendar);
                info_subject.setBottomNavigationView(bottomNavigationView);
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, info_subject).commit();
            }
        });
        holder.bellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClick != null)
                    itemClick.onClick(arrayList.get(position));
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClick != null)
                    itemClick.onDelete(arrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<TimeTable> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView startTime_txt, endTime_txt, name_txt, location_txt;
        private RelativeLayout bellLayout, itemLayout;
        private ConstraintLayout timetableLayout;
        private MaterialCardView cardView;
        private ImageView delete_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            startTime_txt = itemView.findViewById(R.id.start_time);
            endTime_txt = itemView.findViewById(R.id.end_time);
            location_txt = itemView.findViewById(R.id.subject_note);
            name_txt = itemView.findViewById(R.id.subject_name);
            bellLayout = itemView.findViewById(R.id.bellLayout);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            cardView = itemView.findViewById(R.id.subject_layout);
            timetableLayout = itemView.findViewById(R.id.timetableLayout);
            delete_btn = itemView.findViewById(R.id.delete_timetable);
        }
    }
}
