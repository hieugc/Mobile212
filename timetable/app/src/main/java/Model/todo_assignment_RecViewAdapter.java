package Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.R;

import java.util.ArrayList;

public class todo_assignment_RecViewAdapter extends RecyclerView.Adapter<todo_assignment_RecViewAdapter.ViewHolder> {

    private ArrayList<list_check> list_checks;
    private ArrayList<assignment> assignments;
    private RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();

    private FragmentActivity fragmentActivity;
    private View parentView;

    public todo_assignment_RecViewAdapter(View parentView, FragmentActivity fragmentActivity, ArrayList<assignment> assignment, ArrayList<list_check> list_check) {
        this.fragmentActivity = fragmentActivity;
        this.list_checks = list_check;
        this.assignments = assignment;
        this.parentView = parentView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_assignment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position < assignments.size()){
            final assignment ass = assignments.get(position);
            Log.e("a", String.valueOf(ass));
            holder.bindAll(ass, this.list_checks);
        }
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
        assignment assignment;
        TextView content, time_assignment_item;
        CheckBox done;
        androidx.recyclerview.widget.RecyclerView todo_list_assignment_item;
        ScrollView scrollView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.title_assignment_item);
            time_assignment_item = itemView.findViewById(R.id.time_assignment_item);
            done = itemView.findViewById(R.id.check_assignment_item);

            todo_list_assignment_item = itemView.findViewById(R.id.todo_list_assignment_item);
            Log.e("ViewHolder: ", String.valueOf(content));
        }

        public void bindAll(assignment ass, ArrayList<list_check> dlist){
            assignment = ass;
            content.setText(ass.getTitle());
            time_assignment_item.setText(ass.getTime() + " days");
            done.setChecked(ass.getDone());

            todo_check_list_RecViewAdapter adapter = new todo_check_list_RecViewAdapter(fragmentActivity, dlist);
            LinearLayoutManager layoutManager = new LinearLayoutManager(todo_list_assignment_item.getContext());
            layoutManager.setInitialPrefetchItemCount(dlist.size());
            todo_list_assignment_item.setLayoutManager(layoutManager);
            todo_list_assignment_item.setAdapter(adapter);
            todo_list_assignment_item.setRecycledViewPool(pool);
        }
    }
}
