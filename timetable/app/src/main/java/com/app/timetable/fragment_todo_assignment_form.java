package com.app.timetable;

import static com.google.android.material.datepicker.MaterialDatePicker.thisMonthInUtcMilliseconds;
import static com.google.android.material.datepicker.MaterialDatePicker.todayInUtcMilliseconds;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
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
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
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

    //time show
    TextView todo_assignment_form_add_time_start, todo_assignment_form_add_time_end, todo_assignment_form_time_left;

    //dialog
    TextView todo_assignment_form_add_list_head_text_back, todo_assignment_form_add_list_head_text_done, todo_contain_add_list_label_button;
    ImageView todo_assignment_form_add_list_head_back, todo_contain_add_list_button;
    //data
    ArrayList<assignment> assignments;
    ArrayList<list_check> list_checks;

    View assignment_form;

    public void setList_checks(ArrayList<list_check> list_checks){
        this.list_checks = list_checks;
    }
    public void setAssignments(ArrayList<assignment> assignments){
        this.assignments = assignments;
    }

    private static final String TAG = "MyActivity";
    public void setTodoView(fragment_todo view){
        todoView = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assignment_form = inflater.inflate(R.layout.fragment_todo_assignment_form, container, false);
        todo_assignment_form = assignment_form.findViewById(R.id.todo_assignment_form);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.e(TAG, "height = " + displayMetrics.heightPixels + " width = " + displayMetrics.widthPixels);


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

        //offdialog
        todo_assignment_form_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_add_list_dialog();
            }
        });//done
        todo_assignment_form_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_assignment_form_dialog.getVisibility() == View.GONE){
                    showCalendar();
                }
            }
        });//done
        todo_assignment_form_add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_assignment_form_dialog.getVisibility() == View.GONE){
                    open_add_list_dialog();
                    show_recycle_dialog();
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
                if(!list_checks.isEmpty()){
                    if (list_checks.get(list_checks.size() - 1).getContent().trim().equals("")) list_checks.remove(list_checks.size()-1);
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
        if(!list_checks.isEmpty()){
            show_recycle();
            Log.e("check", "he");
        }
        list_item_dialog = assignment_form.findViewById(R.id.list_item_dialog);
        if(!list_checks.isEmpty()){
            show_recycle_dialog();
            Log.e("check", "he");
        }


        //button
        assignment_form_button_done = assignment_form.findViewById(R.id.assignment_form_button_done);
        assignment_form_button_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //tạo assignment

                    //close();
                }
        });


        return assignment_form;
    }
    private void show_recycle_dialog(){
        todo_check_list_form_dialog_RecViewAdapter adapter = new todo_check_list_form_dialog_RecViewAdapter(getActivity(), list_checks, this);
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
                Log.e("check", picker.getHeaderText());
                Log.e("check", String.valueOf(rangedate.length));
                for (String x: rangedate){
                    Log.e("check", String.valueOf(x));
                }

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
        if(!(!list_checks.isEmpty() && list_checks.get((list_checks.size() - 1)).getContent().trim().equals("")) || list_checks.isEmpty()){
            list_check node = new list_check(list_checks.size(), "", false, -1);
            list_checks.add(node);
        }
        show_recycle_dialog();
    }
    private void remove_list_item_dialog(list_check listCheck){
        if(!list_checks.isEmpty()){
            for (int i = listCheck.getId() + 1; i < list_checks.size(); i++){
                list_checks.get(i).setId(i - 1);
            }
            list_checks.remove(listCheck.getId());
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
        todo_assignment_time_show.setVisibility(View.VISIBLE);

        todo_assignment_form_add_time_start.setText(time_start);
        todo_assignment_form_add_time_start.setVisibility(View.VISIBLE);

        todo_assignment_form_add_time_end.setText(time_end);
        todo_assignment_form_add_time_end.setVisibility(View.VISIBLE);

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
        Log.e("check", String.valueOf(view));
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void open_add_list_dialog(){
        todo_assignment_form_dialog.setVisibility(View.VISIBLE);
        todo_assignment_form_dialog_add_list.setVisibility(View.VISIBLE);
        todo_assignment_form.setVisibility(View.GONE);
    }

    @Override
    public void onCheckClick(int pos, meeting meets) {

    }

    @Override
    public void onEditClick(meeting meets) {

    }

    @Override
    public void addListCheck(list_check listCheck) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(!imm.isAcceptingText()){
            list_checks.get(listCheck.getId()).setContent(listCheck.getContent());
            show_e();
            add_list_item_dialog();
        }

        Log.e("check", String.valueOf(imm.isAcceptingText()));
    }
    private void show_e(){
        for (list_check l: list_checks) Log.e("check", l.getId() + " " + l.getContent());
    }
}
