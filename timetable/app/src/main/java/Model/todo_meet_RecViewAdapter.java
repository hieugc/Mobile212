package Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.BKTimeTable;
import com.app.timetable.R;
import com.app.timetable.TimeTableSelRecViewAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
public class todo_meet_RecViewAdapter extends RecyclerView.Adapter<todo_meet_RecViewAdapter.ViewHolder>{

    private ArrayList<meeting> meetings;
    private FragmentActivity fragmentActivity;

    private ItemClickListener listener;

    public todo_meet_RecViewAdapter(FragmentActivity fragmentActivity, ArrayList<meeting> meetings, ItemClickListener listener) {
        this.fragmentActivity = fragmentActivity;
        this.meetings = meetings;
        this.listener = listener;
    }

    public ArrayList<Model.meeting> getMeetings() {
        return meetings;
    }

    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    public ItemClickListener getListener() {
        return listener;
    }

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public void set_todo_Listener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_meet_item, parent, false);
        ViewHolder viewHolder = new ViewHolder((view));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final meeting meet = meetings.get(position);
        if(meet == null) return;

        holder.bind_data(meet);
        holder.title.setText(meet.getTitle());
        holder.time.setText(meet.getTime());
        if(meet.getLocation() == ""){
            holder.f_local_meeting_item.setVisibility(View.GONE);
        }
        else {
            holder.location.setText(meet.getLocation());
        }
        if(meet.getLink() == ""){
            holder.f_link_meeting_item.setVisibility(View.GONE);
        }
        else {
            holder.link.setText(meet.getLink());
        }
        holder.done.setChecked(meet.getDone());
        Log.e("click", "onBindViewHolder: " + holder.done.isClickable());
        holder.done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("check", String.valueOf(b));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public void setMeeting(ArrayList<meeting> meeting) {
        this.meetings = meeting;
        notifyDataSetChanged();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private meeting meets;
        TextView title, time, location, link;
        CheckBox done;
        RelativeLayout f_local_meeting_item, f_link_meeting_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_meeting_item);
            time = itemView.findViewById(R.id.time_meeting_item);
            location = itemView.findViewById(R.id.local_meeting_item);
            link = itemView.findViewById(R.id.link_meeting_item);
            done = itemView.findViewById(R.id.check_meeting_item);
            f_local_meeting_item = itemView.findViewById(R.id.f_local_meeting_item);
            f_link_meeting_item = itemView.findViewById(R.id.f_link_meeting_item);

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, meets);
                }
            });
        }
        private void bind_data(meeting meets){
            this.meets = meets;
        }

    }
}
