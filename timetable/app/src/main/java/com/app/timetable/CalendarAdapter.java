package com.app.timetable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {
    private List<MyCalendar> mCalendar;
    private int recyclecount=0;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtView_calendar_date,txtView_calendar_day;

        public MyViewHolder(View view){
            super(view);
            txtView_calendar_date = view.findViewById(R.id.calendar_date);
            txtView_calendar_day = view.findViewById(R.id.calendar_day);
        }
    }

    public CalendarAdapter(List<MyCalendar> mCalendar) {
        this.mCalendar = mCalendar;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_day_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyCalendar calendar = mCalendar.get(position);

        holder.txtView_calendar_day.setText(calendar.getDay());

        holder.txtView_calendar_date.setText(calendar.getDate());

    }

    @Override
    public int getItemCount() {
        return mCalendar.size();
    }

    @Override
    public void onViewRecycled (MyViewHolder holder){

        recyclecount++;

    }

    private void onScrollChanged(){

    }
}
