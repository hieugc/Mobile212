package Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.R;

import java.util.ArrayList;

public class todo_check_list_RecViewAdapter extends RecyclerView.Adapter<todo_check_list_RecViewAdapter.ViewHolder> {

    private ArrayList<list_check> list_checks = new ArrayList<>();

    private Context context;

    public todo_check_list_RecViewAdapter(Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list_checks.size();
    }

    public void setList_checks(ArrayList<list_check> list_checks) {
        this.list_checks = list_checks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView content;
        CheckBox done;
        ImageView link;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content_list_item);
            done = itemView.findViewById(R.id.check_meeting_item);
            link = itemView.findViewById(R.id.link_list_item);
        }
    }
}
