package Model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.R;

import java.util.ArrayList;

public class todo_list_form_RecViewAdapter extends RecyclerView.Adapter<todo_list_form_RecViewAdapter.ViewHolder> {

    private ArrayList<assignment> assignments;
    private ArrayList<list_check> list_checks;
    private FragmentActivity fragmentActivity;

    private ItemClickListener listener;
    public todo_list_form_RecViewAdapter(FragmentActivity fragmentActivity, ArrayList<assignment> assignment, ArrayList<list_check> list_checks, ItemClickListener listener) {
        this.fragmentActivity = fragmentActivity;
        this.list_checks = list_checks;
        this.assignments = assignment;
        this.listener = listener;
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
            holder.bindAll(ass);
        }
    }

    private ArrayList<list_check> splitArr(ArrayList<list_check> list_checks, int id){
        ArrayList<list_check> res = new ArrayList<>();
        for(list_check index: list_checks){
            if(index.getAssign() == id){
                res.add(index);
            }
        }
        return res;
    }

    @Override
    public int getItemCount() {
        if (assignments == null) return 0;
        return assignments.size();
    }

    public void setAssignments(ArrayList<assignment> assignments) {
        this.assignments = assignments;
        notifyDataSetChanged();
    }

    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public ArrayList<assignment> getAssignments() {
        return assignments;
    }

    public void setList_checks(ArrayList<list_check> list_checks) {
        this.list_checks = list_checks;
    }

    public ArrayList<list_check> getList_checks() {
        return list_checks;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        assignment assignment;
        TextView content, time_assignment_item;
        CheckBox done;
        RecyclerView todo_list_assignment_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.title_assignment_item);
            time_assignment_item = itemView.findViewById(R.id.time_assignment_item);
            done = itemView.findViewById(R.id.check_assignment_item);

            todo_list_assignment_item = itemView.findViewById(R.id.todo_list_assignment_item);

        }

        public void bindAll(assignment ass){
            assignment = ass;
            content.setText(ass.getTitle());
            time_assignment_item.setText(ass.getTime());
            done.setChecked(ass.getDone());

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            todo_list_assignment_item.setLayoutManager(layoutManager);

            todo_check_list_RecViewAdapter adapter = new todo_check_list_RecViewAdapter(fragmentActivity, splitArr(list_checks, ass.getId()), listener);
            todo_list_assignment_item.setAdapter(adapter);
        }
    }
}
