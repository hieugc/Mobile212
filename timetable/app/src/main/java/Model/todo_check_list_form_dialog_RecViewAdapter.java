package Model;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.timetable.R;

import java.util.ArrayList;

public class todo_check_list_form_dialog_RecViewAdapter extends RecyclerView.Adapter<todo_check_list_form_dialog_RecViewAdapter.ViewHolder> {

    private ArrayList<list_check> list_checks;
    private FragmentActivity fragmentActivity;

    private ItemClickListener listener;

    public todo_check_list_form_dialog_RecViewAdapter(FragmentActivity fragmentActivity, ArrayList<list_check> list_checks, ItemClickListener listener) {
        this.fragmentActivity = fragmentActivity;
        this.list_checks = list_checks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_form_dialog_item, parent, false);
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
        ImageView remove_list_item, edit_list_item, result_edit_list_item, refresh_edit_list_item;
        EditText content_list_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remove_list_item = itemView.findViewById(R.id.remove_list_item);
            edit_list_item = itemView.findViewById(R.id.edit_list_item);
            content_list_item = itemView.findViewById(R.id.content_list_item);
            result_edit_list_item = itemView.findViewById(R.id.result_edit_list_item);
            refresh_edit_list_item = itemView.findViewById(R.id.refresh_edit_list_item);
        }
        private void hide(){
            result_edit_list_item.setVisibility(View.GONE);
            refresh_edit_list_item.setVisibility(View.GONE);
            edit_list_item.setVisibility(View.VISIBLE);
        }
        private void visit(){
            result_edit_list_item.setVisibility(View.VISIBLE);
            refresh_edit_list_item.setVisibility(View.VISIBLE);
            edit_list_item.setVisibility(View.GONE);
        }

        public void setAll(list_check list) {
            this.list_checks = list;
            content_list_item.setText(list.getContent());
            content_list_item.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    list_checks.setContent(String.valueOf(editable));
                    listener.addListCheck(list_checks);
                }
            });
            content_list_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if(i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_NEXT){
                        if(keyEvent == null || !keyEvent.isShiftPressed()){
                            list_checks.setContent(String.valueOf(content_list_item.getText()));
                            listener.addListCheck(list_checks);
                            return true;
                        }
                    }
                    else if( i == EditorInfo.IME_ACTION_DONE || keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                        if(keyEvent == null || !keyEvent.isShiftPressed()){
                            InputMethodManager imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(content_list_item.getWindowToken(), 0);
                            list_checks.setContent(String.valueOf(content_list_item.getText()));
                            listener.addListCheck(list_checks);
                            listener.addListCheckItem();
                            return true;
                        }
                    }
                    return false;
                }
            });

            remove_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.removeListCheckItem(list_checks);
                }
            });

            view();

            edit_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //link to note
                    if (!list_checks.getContent().trim().equals("")){
                        listener.linkNewNote(list_checks);
                        view();
                    }
                    else {
                        Toast.makeText(view.getContext(), "nothing", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            result_edit_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.openNote(list_checks, "_dialog");
                }
            });

            refresh_edit_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //remove note
                    listener.unlinkNote(list_checks.getLink());
                    list_checks.setLink(-1);
                    view();
                }
            });
        }
        private void view(){
            if (list_checks.getLink() == -1){
                hide();
            }
            else{
                visit();
            }
        }
    }
}
