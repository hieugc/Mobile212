package com.app.timetable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder>{
    private ArrayList<Date> arrayList = new ArrayList<>();
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private Context context;

    public DateAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_day_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position == 2)
        {
            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#94E0E0")));
//            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
//            layoutParams.height = 30;
//            layoutParams.width = 30;
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(arrayList.get(position));
        String dayOfWeek;
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i)
        {
            case 1:
                dayOfWeek = "CN";
                break;
            case 2:
                dayOfWeek = "T2";
                break;
            case 3:
                dayOfWeek = "T3";
                break;
            case 4:
                dayOfWeek = "T4";
                break;
            case 5:
                dayOfWeek = "T5";
                break;
            case 6:
                dayOfWeek = "T6";
                break;
            case 7:
                dayOfWeek = "T7";
                break;
            default:
                dayOfWeek = "--";
        }
        holder.txtView_calendar_date.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        holder.txtView_calendar_day.setText(dayOfWeek);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date pickedDate = arrayList.get(position);
                Date yesterday = new Date(pickedDate.getTime() - MILLIS_IN_A_DAY);
                Date thePreviousDay = new Date(pickedDate.getTime() - 2 * MILLIS_IN_A_DAY);
                Date tomorrow = new Date(pickedDate.getTime() + MILLIS_IN_A_DAY);
                Date theNextDay = new Date(pickedDate.getTime() + 2 * MILLIS_IN_A_DAY);
                arrayList.clear();
                arrayList.add(thePreviousDay);
                arrayList.add(yesterday);
                arrayList.add(pickedDate);
                arrayList.add(tomorrow);
                arrayList.add(theNextDay);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<Date> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtView_calendar_date,txtView_calendar_day;
        private MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView_calendar_date = itemView.findViewById(R.id.calendar_date);
            txtView_calendar_day = itemView.findViewById(R.id.calendar_day);
            cardView = itemView.findViewById(R.id.calendar_item);
        }
    }
}
