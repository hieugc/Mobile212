package Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.R;

import java.util.ArrayList;

public class todo_item_RecViewAdapter extends RecyclerView.Adapter<todo_item_RecViewAdapter.ViewHolder> {

    private ArrayList<todo_item> items;
    private FragmentActivity fragmentActivity;
    private com.app.timetable.fragment_todo fragment_todo;
    private ItemClickListener listener;

    public todo_item_RecViewAdapter(FragmentActivity fragmentActivity, com.app.timetable.fragment_todo fragment_todo, ArrayList<todo_item> items, ItemClickListener listener) {
        this.fragmentActivity = fragmentActivity;
        this.items = items;
        this.listener = listener;
        this.fragment_todo = fragment_todo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder((view));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final todo_item check = items.get(position);
        if(check == null) return;
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.todo_item.setLayoutManager(layoutManager);
        if (check.getType() == 1){
            ArrayList<meeting> meetings = new ArrayList<>();
            meetings.add(check.getMeetings());
            todo_meet_RecViewAdapter adapter = new todo_meet_RecViewAdapter(fragmentActivity, fragment_todo, meetings, this.listener);
            holder.todo_item.setAdapter(adapter);
        }
        else if(check.getType() == 2){
            ArrayList<assignment> assignments = new ArrayList<>();
            assignments.add(check.getAssignments());
            todo_assignment_RecViewAdapter adapter = new todo_assignment_RecViewAdapter(fragmentActivity, assignments, this.listener);
            holder.todo_item.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        RecyclerView todo_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todo_item = itemView.findViewById(R.id.todo_item);
        }
    }
}
