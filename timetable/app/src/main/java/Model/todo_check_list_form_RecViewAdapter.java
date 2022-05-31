package Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.R;

import java.util.ArrayList;

public class todo_check_list_form_RecViewAdapter extends RecyclerView.Adapter<todo_check_list_form_RecViewAdapter.ViewHolder> {

    private ArrayList<list_check> list_checks;
    private FragmentActivity fragmentActivity;

    private ItemClickListener listener;

    public todo_check_list_form_RecViewAdapter(FragmentActivity fragmentActivity, ArrayList<list_check> list_checks, ItemClickListener listener) {
        this.fragmentActivity = fragmentActivity;
        this.list_checks = list_checks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_form_item, parent, false);
        ViewHolder viewHolder = new ViewHolder((view));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final list_check check = list_checks.get(position);
        if(check == null) return;
        holder.setAll(check);
    }

    @Override
    public int getItemCount() {
        return list_checks.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder{
        list_check list_checks;
        ImageView remove_list_item, edit_list_item;
        TextView content_list_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remove_list_item = itemView.findViewById(R.id.remove_list_item);
            edit_list_item = itemView.findViewById(R.id.edit_list_item);
            content_list_item = itemView.findViewById(R.id.content_list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.openAddListCheck();
                }
            });
        }

        public void setAll(list_check list) {
            this.list_checks = list;
            content_list_item.setText(list.getContent());
            remove_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.removeListCheck(list_checks);
                }
            });
            if(list.getLink() == -1){
                edit_list_item.setVisibility(View.GONE);
            }
            edit_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.openNote(list_checks, "");
                }
            });
        }
    }
}
