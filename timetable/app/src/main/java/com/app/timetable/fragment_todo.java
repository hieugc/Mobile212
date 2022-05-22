package com.app.timetable;

import static android.view.View.VISIBLE;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Model.ItemClickListener;
import Model.assignment;
import Model.list_check;
import Model.meeting;
import Model.todo_assignment_RecViewAdapter;
import Model.todo_item;
import Model.todo_item_RecViewAdapter;

public class fragment_todo extends Fragment implements ItemClickListener{
    public fragment_todo(){
    }
    fragment_todo_meeting_form todo_meeting_form;
    fragment_todo_assignment_form todo_assignment_form;
    ImageView default_todo_layout;
    RecyclerView todo_meeting;

    //dialog
    FloatingActionButton todo_floating_button;
    RelativeLayout todo_floating_button_background;
    Button todo_meeting_button, todo_assignment_button;

    //data
    ArrayList<meeting> meetings;
    //
    ArrayList<assignment> assignments;
    ArrayList<list_check> list_checks;

    public void setList_checks(ArrayList<list_check> list_checks) {
        this.list_checks = list_checks;
    }

    public void setAssignments(ArrayList<assignment> assignments) {
        this.assignments = assignments;
    }

    public void setMeetings(ArrayList<meeting> meetings) {
        this.meetings = meetings;
    }

    public void set_meet_form(fragment_todo_meeting_form form) {
        todo_meeting_form = form;
    }
    public void set_assignment_form(fragment_todo_assignment_form form) {
        todo_assignment_form = form;
    }
    private static final String TAG = "MyActivity";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null){
            String func = bundle.getString("func");
            if(func == "create_meeting"){
                String title = bundle.getString("title");
                String location = bundle.getString("location");
                String link = bundle.getString("link");
                String time = bundle.getString("time");
                String alert = bundle.getString("alert");
                Log.e("create_meeting", title.trim());
                Log.e("create_meeting", location.trim());
                Log.e("create_meeting", link.trim());
                Log.e("create_meeting", time.trim());
                Log.e("create_meeting", alert.trim());

                meeting m = new meeting(
                        this.meetings.get(this.meetings.size() - 1).getId() + 1,
                        time.trim(),
                        title.trim(),
                        location.trim(),
                        link.trim(),
                        alert.trim(),
                        false
                );
                this.meetings.add(m);
                this.setArguments(null);
            }
            else if(func == "remove_meeting"){
                String id = bundle.getString("id");
                Log.e("create_meeting", id.trim());
                int idx = 0;
                for (meeting m: this.meetings){
                    if (m.getId() == Integer.parseInt(id)){
                        this.meetings.remove(idx);
                        break;
                    }
                    idx += 1;
                }
                this.setArguments(null);
            }
            else if(func == "edit_info_meeting"){
                String id = bundle.getString("id");
                String title = bundle.getString("title");
                String location = bundle.getString("location");
                String link = bundle.getString("link");
                String time = bundle.getString("time");
                String alert = bundle.getString("alert");
                String done =  bundle.getString("done");
                Log.e("create_meeting", title.trim());
                Log.e("create_meeting", location.trim());
                Log.e("create_meeting", link.trim());
                Log.e("create_meeting", time.trim());
                Log.e("create_meeting", alert.trim());
                Log.e("create_meeting", id.trim());
                int idx = 0;
                for (meeting m: this.meetings){
                    if (m.getId() == Integer.parseInt(id)){
                        this.meetings.get(idx).setAlert(alert);
                        this.meetings.get(idx).setDone(Boolean.valueOf(done));
                        this.meetings.get(idx).setLink(link);
                        this.meetings.get(idx).setLocation(location);
                        this.meetings.get(idx).setTitle(title);
                        this.meetings.get(idx).setTime(time);
                        break;
                    }
                    idx += 1;
                }
                this.setArguments(null);
            }
            else{
                Log.e("check", "else");
            }
        }

        // Inflate the layout for this fragment
        View todoView = inflater.inflate(R.layout.fragment_todo, container, false);
        //default_layout
        default_todo_layout = todoView.findViewById(R.id.default_todo_layout);
        todo_meeting = todoView.findViewById(R.id.todo_meet_item_recycleView);
        if(assignments == null && meetings == null){
            default_todo_layout.setVisibility(VISIBLE);
            Log.e("check", String.valueOf(assignments));
            Log.e("check", String.valueOf(meetings));
        }
        else{
            default_todo_layout.setVisibility(View.GONE);
            Log.e("check", "nonull");
            item_show();
        }

        todo_floating_button = todoView.findViewById(R.id.todo_float_button);
        todo_floating_button_background = todoView.findViewById(R.id.todo_float_button_background);
        todo_meeting_button = todoView.findViewById(R.id.todo_meeting_button);
        todo_assignment_button = todoView.findViewById(R.id.todo_assignment_button);
        todo_meeting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todo_meeting_form).commit();
                //close_float_button_background();
            }
        });
        todo_assignment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todo_assignment_form).commit();
            }
        });
        todo_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_meeting_button.getVisibility() == View.GONE){
                    open_float_button_background();
                }
                else{
                    close_float_button_background();
                }
            }
        });
        todo_floating_button_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_meeting_button.getVisibility() == VISIBLE){
                    close_float_button_background();
                }
            }
        });
        return todoView;
    }

    private void close_float_button_background(){
        todo_meeting_button.setVisibility(View.GONE);
        todo_assignment_button.setVisibility(View.GONE);

        todo_floating_button_background.getLayoutParams().width = 300;
        todo_floating_button_background.getLayoutParams().height = -2;//wrap_content
        todo_floating_button_background.setBackgroundColor(0);

        todo_floating_button.setImageResource(R.drawable.icon_add);
    }

    private void open_float_button_background(){
        todo_meeting_button.setVisibility(VISIBLE);
        todo_assignment_button.setVisibility(VISIBLE);

        todo_floating_button_background.getLayoutParams().width = -1;//match_parent/match_parent
        todo_floating_button_background.getLayoutParams().height = -1;
        todo_floating_button_background.setBackgroundColor(Color.parseColor("#CC333333"));

        todo_floating_button.setImageResource(R.drawable.icon_close);
    }

    @Override
    public void onCheckClick(int pos, meeting meets) {
        int i = 0;
        for (meeting m: meetings){
            if (m.getId() == meets.getId()){
                meetings.get(i).setDone(meets.getDone());
                break;
            }
            i += 1;
        }
        Log.e("check done", String.valueOf(meets.getDone()));
    }

    @Override
    public void onEditClick(meeting meets) {
        Bundle bundle = new Bundle();
        bundle.putString("func", "edit_meeting");
        bundle.putString("id", String.valueOf(meets.getId()));
        bundle.putString("title", meets.getTitle());
        bundle.putString("location", meets.getLocation());
        bundle.putString("link", meets.getLink());
        bundle.putString("time", meets.getTime());
        bundle.putString("alert", meets.getAlert());
        bundle.putString("done", String.valueOf(meets.getDone()));

        todo_meeting_form.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, todo_meeting_form).commit();
    }

    private void item_show(){
        todo_item_RecViewAdapter adapter = new todo_item_RecViewAdapter(getActivity(), this, createTodo(), this);
        Log.e(TAG, String.valueOf(list_checks));
        todo_meeting.setAdapter(adapter);
        todo_meeting.setLayoutManager(new LinearLayoutManager(todo_meeting.getContext()));
    }
    public void editDoneMeeting(int id, boolean check){
        this.meetings.get(id).setDone(check);
    }

    private ArrayList<todo_item> createTodo(){
        int idx1 = 0, idx2 = 0;
        ArrayList<todo_item> todo_items = new ArrayList<>();
        while (idx2 < this.assignments.size() && idx1 < this.meetings.size()){
            int check = check(this.meetings.get(idx1).getTime(), this.assignments.get(idx2).getTimeStart());
            if(check == 1){
                todo_items.add(new todo_item(null, null, this.meetings.get(idx1)));
                idx1 += 1;
            }
            else if(check == 2){
                todo_items.add(new todo_item(this.assignments.get(idx2), classify_list_check(this.assignments.get(idx2).getId()), null));
                idx2 += 1;
            }
            else {
                todo_items.add(new todo_item(null, null, this.meetings.get(idx1)));
                idx1 += 1;
                todo_items.add(new todo_item(this.assignments.get(idx2), classify_list_check(this.assignments.get(idx2).getId()), null));
                idx2 += 1;
            }
        }

        if(idx1 < this.meetings.size()) {
            while (idx1 < this.meetings.size()){
                todo_items.add(new todo_item(null, null, this.meetings.get(idx1)));
                idx1 += 1;
            }
        }
        else if(idx2 < this.assignments.size()){
            while (idx2 < this.assignments.size()){
                todo_items.add(new todo_item(this.assignments.get(idx2), classify_list_check(this.assignments.get(idx2).getId()), null));
                idx2 += 1;
            }
        }
        return todo_items;
    }

    private ArrayList<list_check> classify_list_check(int id){
        ArrayList<list_check> list_checks = new ArrayList<>();
        for (list_check check: this.list_checks){
            if (check.getAssign() == id){
                list_checks.add(check);
            }
        }
        return list_checks;
    }

    private int check(String date1, String date2){
        //year
        if(getYear(date1) > getYear(date2)) return 1;
        else if(getYear(date1) < getYear(date2)) return 2;
        else {
            if(getMonth(date1) > getMonth(date2)) return 1;
            else if(getMonth(date1) < getMonth(date2)) return 2;
            else {
                if(getDay(date1) > getDay(date2)) return 1;
                else if(getDay(date1) < getDay(date2)) return 2;
            }
        }
        return 0;
    }
    private int getYear(String date){
        final int i = Integer.parseInt(date.split("/")[2]);
        return i;
    }
    private int getMonth(String date){
        final int i = Integer.parseInt(date.split("/")[1]);
        return i;
    }
    private int getDay(String date){
        final int i = Integer.parseInt(date.split("/")[0]);
        return i;
    }
}