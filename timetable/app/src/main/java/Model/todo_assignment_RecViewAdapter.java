package Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.R;

import java.util.ArrayList;

public class todo_assignment_RecViewAdapter extends RecyclerView.Adapter<todo_assignment_RecViewAdapter.ViewHolder> {

    private ArrayList<assignment> assignments;
    private FragmentActivity fragmentActivity;

    private ItemClickListener listener;
    public todo_assignment_RecViewAdapter(FragmentActivity fragmentActivity, ArrayList<assignment> assignment, ItemClickListener listener) {
        this.fragmentActivity = fragmentActivity;
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


    @Override
    public int getItemCount() {
        if (assignments == null) return 0;
        return assignments.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder{
        assignment assignment;
        TextView content, time_assignment_item;
        CheckBox done;
        RecyclerView todo_list_assignment_item;

        ImageView edit_asssignment_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.title_assignment_item);
            time_assignment_item = itemView.findViewById(R.id.time_assignment_item);
            done = itemView.findViewById(R.id.check_assignment_item);

            todo_list_assignment_item = itemView.findViewById(R.id.todo_list_assignment_item);
            edit_asssignment_item = itemView.findViewById(R.id.edit_asssignment_item);
        }

        public void bindAll(assignment ass){
            assignment = ass;
            content.setText(ass.getTitle());
            time_assignment_item.setText(ass.getTime());
            boolean check = true;
            for(list_check l: ass.getList_checks())
            {
                if(!l.getDone())
                    check = false;
            }
            done.setChecked(check);
            ass.setDone(check);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    assignment.setDone(!assignment.getDone());
                    for (int j = 0; j < assignment.getList_checks().size(); j ++){
                        assignment.getList_checks().get(j).setDone(assignment.getDone());
                    }
                    resetView();
                    listener.onCheckAssign(assignment);
                }
            });

            resetView();
            edit_asssignment_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.editAssignment(assignment);
                }
            });
        }
        public void resetView(){
            if(assignment.getList_checks().size() != 0){
                LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
                todo_list_assignment_item.setLayoutManager(layoutManager);

                todo_check_list_RecViewAdapter adapter = new todo_check_list_RecViewAdapter(fragmentActivity, assignment.getList_checks(), listener);
                todo_list_assignment_item.setAdapter(adapter);
            }
        }
    }
}
