package Model;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void setList_checks(ArrayList<list_check> list_checks) {
        this.list_checks = list_checks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        list_check list_checks;
        ImageView remove_list_item, edit_list_item;
        EditText content_list_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remove_list_item = itemView.findViewById(R.id.remove_list_item);
            edit_list_item = itemView.findViewById(R.id.edit_list_item);
            content_list_item = itemView.findViewById(R.id.content_list_item);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                content_list_item.setFocusedByDefault(false);
            }
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
                            Log.e("check", "true");
                            list_checks.setContent(String.valueOf(content_list_item.getText()));
                            listener.addListCheck(list_checks);
                            return true;
                        }
                    }
                    else if( i == EditorInfo.IME_ACTION_DONE || keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                        if(keyEvent == null || !keyEvent.isShiftPressed()){
                            Log.e("check", "true");
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
        }
    }
}
