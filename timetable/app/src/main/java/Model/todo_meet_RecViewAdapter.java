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
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.BKTimeTable;
import com.app.timetable.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
public class todo_meet_RecViewAdapter extends RecyclerView.Adapter<todo_meet_RecViewAdapter.ViewHolder> {

    private ArrayList<meeting> meeting = new ArrayList<>();
    private Context context;

    public todo_meet_RecViewAdapter(Context context) {
        this.context = context;
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
        final meeting meet = meeting.get(position);
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
        holder.done.setSelected(meet.getDone());
        holder.done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("check", "onCheckedChanged: " + b );
            }
        });
    }

    @Override
    public int getItemCount() {
        return meeting.size();
    }

    public void setMeeting(ArrayList<meeting> meeting) {
        this.meeting = meeting;
        notifyDataSetChanged();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
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
        }
    }
}
