package com.app.timetable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TimeTableSelRecViewAdapter extends RecyclerView.Adapter<TimeTableSelRecViewAdapter.ViewHolder> {


    private ArrayList<BKTimeTable> timetable = new ArrayList<>();
    private FragmentActivity fragmentActivity;

    public interface OnItemsClickListener{
        void onItemClick(BKTimeTable bkTimeTable);
    }
    private ArrayList<String> bkTimeTableList;
    private OnItemsClickListener listener;

    public TimeTableSelRecViewAdapter(FragmentActivity fragmentActivity, ArrayList<String> bkTimeTableList) {
        this.fragmentActivity = fragmentActivity;
        this.bkTimeTableList = bkTimeTableList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_list_item_selection, parent, false);
        ViewHolder viewHolder = new ViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.header_txt.setText(timetable.get(position).getName() +" - "+ timetable.get(position).getGroup());
        holder.location_txt.setText(timetable.get(position).getLocation());
        holder.date_txt.setText(timetable.get(position).getDate());
        holder.time_txt.setText(timetable.get(position).getTime());
        holder.week_txt.setText(timetable.get(position).getWeek());
        Log.d("List: ", bkTimeTableList.toString());
        Log.d("Id", String.valueOf(timetable.get(position).getName()));
        for(int i = 0; i < bkTimeTableList.size(); i++) {
            if (bkTimeTableList.get(i).equals(timetable.get(position).getName()))
            {
                holder.btn_select.setChecked(true);
            }
        }
        holder.btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ListTT", timetable.toString());
                Log.d("ListBK", bkTimeTableList.toString());
                if (holder.btn_select.isChecked()) {
                    bkTimeTableList.add(timetable.get(position).getName());
                } else {
                    bkTimeTableList.remove(timetable.get(position).getName());
                }
                if (listener != null)
                {
                    listener.onItemClick(timetable.get(position));
                }
            }
        });
    }

    public void setList(ArrayList<String> bkTimeTableList) {
        this.bkTimeTableList = bkTimeTableList;
        notifyDataSetChanged();
    }

    public void setListener(OnItemsClickListener listener) {
        this.listener = listener;
    }

    public ArrayList<String> getBkTimeTableList() {
        return bkTimeTableList;
    }

    public ArrayList<BKTimeTable> getTimetable() {
        return timetable;
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
        private CheckBox btn_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header_txt = itemView.findViewById(R.id.name_selection);
            location_txt = itemView.findViewById(R.id.location_selection_txt);
            date_txt = itemView.findViewById(R.id.date_selection_txt);
            time_txt = itemView.findViewById(R.id.time_selection_txt);
            week_txt = itemView.findViewById(R.id.week_selection_txt);
            btn_select = itemView.findViewById(R.id.btn_radio);
        }
    }
}
