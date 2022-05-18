package com.app.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class TimeTableRecViewAdapter extends RecyclerView.Adapter<TimeTableRecViewAdapter.ViewHolder> {


    private ArrayList<BKTimeTable> timetable = new ArrayList<>();

    private FragmentActivity fragmentActivity;

    private BkTimeTableSelectionFragment bkTimeTableSelectionFragment;

    public TimeTableRecViewAdapter(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.header_txt.setText(timetable.get(position).getName());
        holder.location_txt.setText(timetable.get(position).getLocation());
        holder.date_txt.setText(timetable.get(position).getDate());
        holder.time_txt.setText(timetable.get(position).getTime());
        holder.week_txt.setText(timetable.get(position).getWeek());
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                bkTimeTableSelectionFragment = new BkTimeTableSelectionFragment(timetable.get(position).getName(), timetable.get(position).getUserID());
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, bkTimeTableSelectionFragment).commit();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return timetable.size();
    }

    public void setTimetable(ArrayList<BKTimeTable> timetable) {
        this.timetable = timetable;
        notifyDataSetChanged();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView header_txt, location_txt, date_txt, time_txt, week_txt;
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header_txt = itemView.findViewById(R.id.name);
            location_txt = itemView.findViewById(R.id.location_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            time_txt = itemView.findViewById(R.id.time_txt);
            week_txt = itemView.findViewById(R.id.week_txt);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
