package com.app.timetable;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import Model.ItemClickListener;
import Model.assignment;
import Model.list_check;
import Model.meeting;
import Model.todo_check_list_form_RecViewAdapter;
import Model.todo_check_list_form_dialog_RecViewAdapter;

public class fragment_todo_assignment_form extends Fragment implements ItemClickListener {

    public fragment_todo_assignment_form(){
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain_todo_form, this).commit();
    }
    ConstraintLayout todo_assignment_form_dialog;
    RelativeLayout todo_assignment_form_dialog_add_list, todo_assignment_form;
    Button assignment_form_button_done;
    ImageView todo_assignment_form_close, todo_assignment_form_add_time, todo_assignment_form_add_list;
    EditText assignment_form_sub;
    fragment_todo todoView;
    LinearLayout todo_assignment_time_show;
    RecyclerView list_item_form, list_item_dialog;
    String _bundle_ = "";
    int _id_ = -1;
    private BottomNavigationView bottomNavigationView;
    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    Button assignment_form_button_remove;

    //time show
    TextView todo_assignment_form_add_time_start, todo_assignment_form_add_time_end, todo_assignment_form_time_left;

    //dialog
    TextView todo_assignment_form_add_list_head_text_back, todo_assignment_form_add_list_head_text_done, todo_contain_add_list_label_button;
    ImageView todo_assignment_form_add_list_head_back, todo_contain_add_list_button;
    //data
    ArrayList<list_check> list_checks;
    ArrayList<list_check> list_checks_dialog;
    ArrayList<Note> list_note;

    View assignment_form;

    public void setList_note(ArrayList<Note> list_note) {
        this.list_note = list_note;
    }

    public void setList_checks_dialog(ArrayList<list_check> list_checks_dialog) {
        this.list_checks_dialog = list_checks_dialog;
    }

    public void setList_checks(ArrayList<list_check> list_checks){
        this.list_checks = list_checks;
    }

    private static final String TAG = "MyActivity";
    public void setTodoView(fragment_todo view){
        todoView = view;
    }

    public fragment_todo getTodoView() {
        return todoView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();
        if (list_checks == null){
            list_checks = new ArrayList<>();
        }
        if (list_checks_dialog == null){
            list_checks_dialog = new ArrayList<>();
        }
        if(list_note == null){
            list_note = new ArrayList<>();
        }
        if(bundle != null){
            String func = bundle.getString("func");
            if (func.trim().equals("edit_assignment")){
                this._bundle_ = "edit_assignment";
                assignment_form_sub.setText(bundle.getString("title"));
                time_show("Bắt đầu: " + bundle.getString("time_start"), "Kết thúc: " + bundle.getString("time_end"), bundle.getString("time_left"));
                this.list_note = bundle.getParcelableArrayList("list_note");
                this.list_checks = bundle.getParcelableArrayList("list_checks");
                this.list_checks_dialog = bundle.getParcelableArrayList("list_checks_dialog");

                int id =  bundle.getInt("id");
                show_recycle();
                assignment_form_button_remove.setVisibility(View.VISIBLE);
                assignment_form_button_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("func", "remove_assignment");
                        bundle.putInt("id", id);
                        todoView.setArguments(bundle);
                        assignment_form_sub.setText("");
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
                    }
                });
                assignment_form_button_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkValidation()){
                            //tạo assignment
                            Bundle bundle = new Bundle();
                            bundle.putString("func", "edit_assignment");
                            bundle.putString("id", String.valueOf(id));
                            bundle.putString("title", assignment_form_sub.getText().toString());
                            bundle.putString("time_start", todo_assignment_form_add_time_start.getText().toString().split(": ")[1]);
                            bundle.putString("time_end", todo_assignment_form_add_time_end.getText().toString().split(": ")[1]);

                            bundle.putParcelableArrayList("list_check", (ArrayList<? extends Parcelable>) list_checks);
                            bundle.putParcelableArrayList("list_note", (ArrayList<? extends Parcelable>) list_note);
                            todoView.setArguments(bundle);
                            assignment_form_sub.setText("");
                            getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
                        }
                    }
                });
            }
            else if(func.trim().equals("linked_note")){
                getLastState(bundle);
                list_check listCheck = bundle.getParcelable("listCheck");
                Note note = bundle.getParcelable("note");
                checkBundle(bundle);
                if (note != null){
                    update_note(note, listCheck, list_checks_dialog);
                }
                open_add_list_dialog();
                show_recycle_dialog();
            }
            else if(func.trim().equals("opened_note_dialog")){
                getLastState(bundle);
                list_check listCheck = bundle.getParcelable("listCheck");
                Note note = bundle.getParcelable("note");
                checkBundle(bundle);
                update_note(note, listCheck, list_checks_dialog);

                open_add_list_dialog();
                show_recycle_dialog();
            }
            else if(func.trim().equals("opened_note")){
                getLastState(bundle);
                list_check listCheck = bundle.getParcelable("listCheck");
                Note note = bundle.getParcelable("note");
                checkBundle(bundle);
                update_note(note, listCheck, list_checks);
                show_recycle();
            }
            this.setArguments(null);
        }
        else {
            assignment_form_sub.setText("");
        }

        todo_assignment_form_add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_assignment_form_dialog.getVisibility() == View.GONE){
                    open_add_list_dialog();
                    changeData();
                    show_recycle_dialog();
                }
            }
        });
    }
    private void checkBundle(Bundle bundle){
        if (bundle.getString("bundle").trim().equals("edit_assignment")){
            _bundle_ = bundle.getString("bundle").trim();
            _id_ = bundle.getInt("_id_");
            assignment_form_button_remove.setVisibility(View.VISIBLE);
            assignment_form_button_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("func", "remove_assignment");
                    bundle.putInt("id", bundle.getInt("_id_"));
                    todoView.setArguments(bundle);

                    assignment_form_sub.setText("");
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
                }
            });
            assignment_form_button_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //tạo assignment
                    Bundle bundle = new Bundle();
                    bundle.putString("func", "edit_assignment");
                    bundle.putString("id", String.valueOf(bundle.getInt("_id_")));
                    bundle.putString("title", assignment_form_sub.getText().toString());
                    bundle.putString("time_start", todo_assignment_form_add_time_start.getText().toString().split(": ")[1]);
                    bundle.putString("time_end", todo_assignment_form_add_time_end.getText().toString().split(": ")[1]);

                    bundle.putParcelableArrayList("list_check", (ArrayList<? extends Parcelable>) list_checks);
                    bundle.putParcelableArrayList("list_note", (ArrayList<? extends Parcelable>) list_note);
                    todoView.setArguments(bundle);
                    assignment_form_sub.setText("");

                    assignment_form_sub.setText("");
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
                }
            });
        }
    }
    private void changeData(){
        this.list_checks_dialog.clear();
        for (list_check l: this.list_checks){
            this.list_checks_dialog.add(l);
        }
    }
    private void getLastState(Bundle bundle){
        this.list_checks = bundle.getParcelableArrayList("list_checks");
        this.list_checks_dialog = bundle.getParcelableArrayList("list_checks_dialog");
        this.list_note = bundle.getParcelableArrayList("list_note");
        this.todoView = bundle.getParcelable("todoView");
        assignment_form_sub.setText(bundle.getString("sub"));
        time_show(bundle.getString("time_start"), bundle.getString("time_end"), bundle.getString("time_left"));
    }
    private void update_note(Note note, list_check listCheck, ArrayList<list_check> list_checks){
        for (int i = 0; i < list_checks.size(); i++){
            if(list_checks.get(i) != null && list_checks.get(i).getId() == listCheck.getId()){
                list_note.remove(i);
                note.setId(i);
                list_note.add(i, note);
                if (note != null){
                    list_checks.get(i).setLink(i);
                }
                else {
                    list_checks.get(i).setLink(-1);
                }
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bottomNavigationView.setForeground(null);
        // Inflate the layout for this fragment

        assignment_form = inflater.inflate(R.layout.fragment_todo_assignment_form, container, false);
        todo_assignment_form = assignment_form.findViewById(R.id.todo_assignment_form);

        assignment_form_button_remove = assignment_form.findViewById(R.id.assignment_form_button_remove);
        assignment_form_button_remove.setVisibility(View.GONE);

        todo_assignment_form_time_left = assignment_form.findViewById(R.id.todo_assignment_form_time_left);
        todo_assignment_form_add_time_start = assignment_form.findViewById(R.id.todo_assignment_form_add_time_start);
        todo_assignment_form_add_time_end = assignment_form.findViewById(R.id.todo_assignment_form_add_time_end);
        todo_assignment_time_show = assignment_form.findViewById(R.id.todo_assignment_time_show);

        //close
        todo_assignment_form_close = assignment_form.findViewById(R.id.todo_assignment_form_close);
                todo_assignment_form_close.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       close();
                    }
                });
        //title
        assignment_form_sub = assignment_form.findViewById(R.id.assignment_form_sub);

        //icon click
        todo_assignment_form_add_time = assignment_form.findViewById(R.id.todo_assignment_form_add_time);
        todo_assignment_form_add_list = assignment_form.findViewById(R.id.todo_assignment_form_add_list);

        //dialog
        todo_assignment_form_dialog = assignment_form.findViewById(R.id.todo_assignment_form_dialog);
        todo_assignment_form_dialog_add_list = assignment_form.findViewById(R.id.todo_assignment_form_dialog_add_list);

        //onclicklist

        todo_assignment_form_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_assignment_form_dialog.getVisibility() == View.GONE){
                    closeKeyBoard();
                    showCalendar();
                }
            }
        });//done

        todo_assignment_form_add_list_head_text_back = assignment_form.findViewById(R.id.todo_assignment_form_add_list_head_text_back);
        todo_assignment_form_add_list_head_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close dialog
                close_add_list_dialog();
            }
        });
        todo_assignment_form_add_list_head_back = assignment_form.findViewById(R.id.todo_assignment_form_add_list_head_back);
        todo_assignment_form_add_list_head_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close dialog
                close_add_list_dialog();
            }
        });
        todo_assignment_form_add_list_head_text_done = assignment_form.findViewById(R.id.todo_assignment_form_add_list_head_text_done);
        todo_assignment_form_add_list_head_text_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add item
                //close dialog
                close_add_list_dialog();
                if( list_checks_dialog != null){
                    remove_list_item_dialog_null(list_checks_dialog);
                }
                list_checks.clear();
                for (list_check l: list_checks_dialog){
                    list_checks.add(l);
                }
                show_recycle();
            }
        });
        //
        todo_contain_add_list_label_button = assignment_form.findViewById(R.id.todo_contain_add_list_label_button);
        todo_contain_add_list_label_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_list_item_dialog();
            }
        });

        todo_contain_add_list_button = assignment_form.findViewById(R.id.todo_contain_add_list_button);
        todo_contain_add_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_list_item_dialog();
            }
        });


        //show list
        list_item_form = assignment_form.findViewById(R.id.list_item_form);
        list_item_dialog = assignment_form.findViewById(R.id.list_item_dialog);

        //button
        assignment_form_button_done = assignment_form.findViewById(R.id.assignment_form_button_done);
        assignment_form_button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()){
                    //tạo assignment
                    Bundle bundle = new Bundle();
                    bundle.putString("func", "create_assignment");
                    bundle.putString("title", assignment_form_sub.getText().toString());
                    bundle.putString("time_start", todo_assignment_form_add_time_start.getText().toString().split(": ")[1]);
                    bundle.putString("time_end", todo_assignment_form_add_time_end.getText().toString().split(": ")[1]);
                    bundle.putParcelableArrayList("list_check", (ArrayList<? extends Parcelable>) list_checks);
                    bundle.putParcelableArrayList("list_note", (ArrayList<? extends Parcelable>) list_note);

                    todoView.setArguments(bundle);
                    assignment_form_sub.setText("");
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
                    //close();
                }
            }
        });
        return assignment_form;
    }
    private boolean checkValidation(){
        if (assignment_form_sub.getText().toString().trim().equals("")){
            assignment_form_sub.setError("Hãy nhập tiêu đề");
            return false;
        }
        if (todo_assignment_form_add_time_end.getText().toString().trim().equals("") || todo_assignment_form_add_time_start.getText().toString().equals("")){

            todo_assignment_time_show.setVisibility(View.VISIBLE);
            todo_assignment_form_add_time_end.setText("Hãy chọn thời gian");
            todo_assignment_form_add_time_end.setVisibility(View.VISIBLE);
            todo_assignment_form_add_time_end.setTextColor(ResourcesCompat.getColor(getResources(), R.color.error_form, null));
            return false;
        }
        return true;
    }
    private void show_recycle_dialog(){
        todo_check_list_form_dialog_RecViewAdapter adapter = new todo_check_list_form_dialog_RecViewAdapter(getActivity(), list_checks_dialog, this);
        list_item_dialog.setAdapter(adapter);
        list_item_dialog.setLayoutManager(new LinearLayoutManager(list_item_dialog.getContext()));
    }
    private void show_recycle(){
        todo_check_list_form_RecViewAdapter adapter = new todo_check_list_form_RecViewAdapter(getActivity(), list_checks, this);
        list_item_form.setAdapter(adapter);
        list_item_form.setLayoutManager(new LinearLayoutManager(list_item_form.getContext()));
    }
    private void close(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
    }

    private void showCalendar(){
        //Date Range Picker
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        calendar.add(Calendar.YEAR, 1);
        long next_year = calendar.getTimeInMillis();


        CalendarConstraints.Builder builder = new CalendarConstraints.Builder();
        builder.setOpenAt(today);
        builder.setStart(today);
        builder.setEnd(next_year);
        builder.setValidator(DateValidatorPointForward.now());


        MaterialDatePicker picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Chọn ngày")
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .setCalendarConstraints(builder.build())
                .build();

        //SHOW DATE PICKER
        picker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                String[] rangedate = picker.getHeaderText().split(" ",7);
                if(rangedate.length == 5){
                    String time_start = "Bắt đầu: " + hash_day(rangedate[1]) + "/" + hash_month(rangedate[0]) + "/2022";
                    String time_end = "Kết thúc: " + hash_day(rangedate[4]) + "/" + hash_month(rangedate[3]) + "/2022";
                    int nday_1 = numOfday(2022, Integer.parseInt(hash_month(rangedate[0])), Integer.parseInt(hash_day(rangedate[1])));
                    int nday_2 = numOfday(2022, Integer.parseInt(hash_month(rangedate[3])), Integer.parseInt(hash_day(rangedate[4])));
                    String time = String.valueOf(nday_2 - nday_1) + " ngày";
                    time_show(time_start, time_end, time);
                }
                else if(rangedate.length == 6){
                    String time_start = "Bắt đầu: " + hash_day(rangedate[1]) + "/" + hash_month(rangedate[0]) + "/2022";
                    String time_end = "Kết thúc: " + hash_day(rangedate[4].replace(",", " ")) + "/" + hash_month(rangedate[3]) + "/2023";
                    int nday_1 = numOfday(2022, Integer.parseInt(hash_month(rangedate[0])), Integer.parseInt(hash_day(rangedate[1])));
                    int nday_2 = numOfday(2023, Integer.parseInt(hash_month(rangedate[3])), Integer.parseInt(hash_day(rangedate[4].replace(",", " "))));
                    String time = String.valueOf(nday_2 - nday_1) + " ngày";
                    time_show(time_start, time_end, time);
                }
                else if(rangedate.length == 7){
                    String time_start = "Bắt đầu: " + hash_day(rangedate[1].replace(",", " ")) + "/" + hash_month(rangedate[0]) + "/2022";
                    String time_end = "Kết thúc: " + hash_day(rangedate[5].replace(",", " ")) + "/" + hash_month(rangedate[4]) + "/2023";
                    int nday_1 = numOfday(2023, Integer.parseInt(hash_month(rangedate[0])), Integer.parseInt(hash_day(hash_day(rangedate[1].replace(",", " ")))));
                    int nday_2 = numOfday(2023, Integer.parseInt(hash_month(rangedate[4])), Integer.parseInt(hash_day(rangedate[5].replace(",", " "))));
                    String time = String.valueOf(nday_2 - nday_1) + " ngày";
                    time_show(time_start, time_end, time);
                }
            }
        });
    }
    private int numOfday(int y, int m, int d){
        m -= 1;
        int res = d;
        while (m > 0){
            res += dayOfmonth(m);
            m-= 1;
        }
        if (y == 2023) res += 365;
        return res;
    }
    private int dayOfmonth(int m){
        switch (m){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2: return 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return 0;
    }
    private String hash_day(String day){
        if(Integer.parseInt(day.trim()) < 10){
            return "0" + day.trim();
        }
        return day.trim();
    }

    private void add_list_item_dialog(){
        if(!(!list_checks_dialog.isEmpty() && list_checks_dialog.get((list_checks_dialog.size() - 1)).getContent().trim().equals("")) || list_checks_dialog.isEmpty()){
            list_check node = new list_check(list_checks_dialog.size() , "", false, -1);
            list_checks_dialog.add(node);
            list_note.add(null);
        }
        show_recycle_dialog();
    }
    private void remove_list_item_dialog_id(list_check listCheck, ArrayList<list_check> list_checks){
        if(!list_checks.isEmpty()){
            for (int i = 0; i < list_checks.size(); i++){
                if (list_checks.get(i).getId() == listCheck.getId()){
                    Log.e("check_l", String.valueOf(list_checks));
                    Log.e("check_n", String.valueOf(list_note));
                    list_checks.remove(i);
                    if (list_note != null){
                        list_note.remove(i);
                    }
                    break;
                }
            }
        }
    }
    private void remove_list_item_dialog_null(ArrayList<list_check> list_checks){
        if(!list_checks.isEmpty()){
            for (int i = 0; i < list_checks.size(); i++){
                if(list_checks.get(i).getContent().trim().equals("")){
                    Log.e("check_l", String.valueOf(list_checks));
                    Log.e("check_n", String.valueOf(list_note));
                    list_checks.remove(i);
                    if (list_note != null){
                        list_note.remove(i);
                    }
                    i -= 1;
                }
            }
        }
    }

    private String hash_month(String month){
        if (month.trim().toUpperCase().indexOf("JAN") != -1) return "01";
        if (month.trim().toUpperCase().indexOf("FEB") != -1) return "02";
        if (month.trim().toUpperCase().indexOf("MAR") != -1) return "03";
        if (month.trim().toUpperCase().indexOf("APR") != -1) return "04";
        if (month.trim().toUpperCase().indexOf("MAY") != -1) return "05";
        if (month.trim().toUpperCase().indexOf("JUN") != -1) return "06";
        if (month.trim().toUpperCase().indexOf("JUL") != -1) return "07";
        if (month.trim().toUpperCase().indexOf("AUG") != -1) return "08";
        if (month.trim().toUpperCase().indexOf("SEP") != -1) return "09";
        if (month.trim().toUpperCase().indexOf("OCT") != -1) return "10";
        if (month.trim().toUpperCase().indexOf("NOV") != -1) return "11";
        if (month.trim().toUpperCase().indexOf("DEC") != -1) return "12";
        return month;
    }

    private void time_show(CharSequence time_start, CharSequence time_end, CharSequence time_left){
        if(time_start.equals("") || time_end.equals("") || time_left.equals("")) return;

        todo_assignment_time_show.setVisibility(View.VISIBLE);
        todo_assignment_form_add_time_start.setText(time_start);
        todo_assignment_form_add_time_start.setVisibility(View.VISIBLE);

        todo_assignment_form_add_time_end.setText(time_end);
        todo_assignment_form_add_time_end.setVisibility(View.VISIBLE);
        todo_assignment_form_add_time_end.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));


        todo_assignment_form_time_left.setText(time_left);
        todo_assignment_form_time_left.setVisibility(View.VISIBLE);
    }
    private void close_add_list_dialog(){
        closeKeyBoard();
        todo_assignment_form_dialog.setVisibility(View.GONE);
        todo_assignment_form_dialog_add_list.setVisibility(View.GONE);
        todo_assignment_form.setVisibility(View.VISIBLE);
    }
    private void closeKeyBoard(){
        View view = assignment_form.findFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void open_add_list_dialog(){
        closeKeyBoard();
        todo_assignment_form_dialog.setVisibility(View.VISIBLE);
        todo_assignment_form_dialog_add_list.setVisibility(View.VISIBLE);
        todo_assignment_form.setVisibility(View.GONE);
    }

    @Override
    public void onCheckClick(meeting meets) {

    }

    @Override
    public void onEditClick(meeting meets) {

    }

    @Override
    public void addListCheck(list_check listCheck) {
        for (list_check l: list_checks_dialog){
            if (l.getId() == listCheck.getId()){
                l.setContent(listCheck.getContent());
                break;
            }
        }
    }

    @Override
    public void addListCheckItem() {
        remove_list_item_dialog_null(list_checks_dialog);
        add_list_item_dialog();
    }

    @Override
    public void removeListCheckItem(list_check listCheck) {
        remove_list_item_dialog_id(listCheck, list_checks_dialog);
        show_recycle_dialog();
    }

    @Override
    public void removeListCheck(list_check listCheck) {
        remove_list_item_dialog_id(listCheck, list_checks);
        show_recycle();
    }

    @Override
    public void openAddListCheck() {
        if(todo_assignment_form_dialog.getVisibility() == View.GONE){
            open_add_list_dialog();
            changeData();
            show_recycle_dialog();
        }
    }

    @Override
    public void editAssignment(assignment assign) {

    }

    @Override
    public void onCheckAssign(assignment assign) {

    }

    @Override
    public int linkNewNote(list_check listCheck) {
        Bundle bundle = new Bundle();
        bundle.putString("func", "link_note");
        bundle.putString("title", listCheck.getContent());
        bundle.putParcelable("listCheck", listCheck);

        addBundle(bundle);

        AddnoteFragment addnoteFragment = new AddnoteFragment();
        addnoteFragment.setArguments(bundle);
        addnoteFragment.setBottomNavigationView(bottomNavigationView);
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, addnoteFragment).commit();

        return 0;
    }
    private void addBundle(Bundle bundle){
        bundle.putString("head_back", "Hủy");
        bundle.putString("head_add", "Thêm");
        bundle.putParcelableArrayList("list_checks", this.list_checks);
        bundle.putParcelableArrayList("list_checks_dialog", this.list_checks_dialog);
        bundle.putParcelableArrayList("list_note", this.list_note);
        bundle.putParcelable("todoView", this.todoView);
        bundle.putString("sub", assignment_form_sub.getText().toString());
        bundle.putString("time_start", todo_assignment_form_add_time_start.getText().toString());
        bundle.putString("time_end", todo_assignment_form_add_time_end.getText().toString());
        bundle.putString("time_left", todo_assignment_form_time_left.getText().toString());
        bundle.putString("bundle", _bundle_);
        bundle.putInt("_id_", _id_);
    }

    @Override
    public void unlinkNote(int id) {
        for (int i = 0; i < list_note.size(); i++){
            if (list_note.get(i) != null && list_note.get(i).getId() == id){
                list_note.remove(i);
                list_note.add(i, null);
                list_checks_dialog.get(i).setLink(-1);
                break;
            }
        }
    }

    @Override
    public void openNote(list_check listCheck, String type) {
        for (int i = 0; i < list_note.size(); i++){
            if (list_note.get(i) != null && list_note.get(i).getId() == listCheck.getLink()){
                Bundle bundle = new Bundle();
                bundle.putString("func", "open_note");
                bundle.putString("title", list_note.get(i).getTitle());
                bundle.putString("content", list_note.get(i).getContent());
                bundle.putParcelable("note", list_note.get(i));
                bundle.putParcelable("listCheck", listCheck);
                bundle.putString("type", type);
                addBundle(bundle);

                AddnoteFragment addnoteFragment = new AddnoteFragment();
                addnoteFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, addnoteFragment).commit();
                break;
            }
        }
    }

    @Override
    public void openNoteView(list_check listCheck) {

    }
    private void resetForm(){

    }
}
