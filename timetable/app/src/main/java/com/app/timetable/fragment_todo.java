package com.app.timetable;

import static android.view.View.VISIBLE;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import Model.ItemClickListener;
import Model.assignment;
import Model.list_check;
import Model.meeting;
import Model.todo_item;
import Model.todo_item_RecViewAdapter;

@SuppressLint("ParcelCreator")
public class fragment_todo extends Fragment implements ItemClickListener, Parcelable {


    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private static final long MILLIS_IN_AN_HOUR = 1000 * 60 * 60;
    private static final long MILLIS_IN_AN_MINUTE = 1000 * 60;

    private MeetingAlarmReceiver meetingAlarmReceiver;
    private AssignmentAlarmReceiver assignmentAlarmReceiver;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;


    public fragment_todo(){
    }
    private fragment_todo_meeting_form todo_meeting_form;
    private fragment_todo_assignment_form todo_assignment_form;
    private ImageView default_todo_layout;
    private RecyclerView todo_meeting;

    private DataBaseHelper dataBaseHelper;

    public void setDataBaseHelper(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    //dialog
    private FloatingActionButton todo_floating_button;
    private RelativeLayout todo_floating_button_background;
    private Button todo_meeting_button, todo_assignment_button;
    //data
    private ArrayList<meeting> meetings;
    private ArrayList<assignment> assignments;

    private BottomNavigationView bottomNavigationView;
    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
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

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    private static final String TAG = "MyActivity";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bottomNavigationView.setForeground(null);
        // Inflate the layout for this fragment
        View todoView = inflater.inflate(R.layout.fragment_todo, container, false);
        dataBaseHelper = new DataBaseHelper(todoView.getContext());
        //default_layout
        default_todo_layout = todoView.findViewById(R.id.default_todo_layout);
        todo_meeting = todoView.findViewById(R.id.todo_meet_item_recycleView);

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
                todo_assignment_form.setList_checks(new ArrayList<>());
                todo_assignment_form.setList_checks_dialog(new ArrayList<>());
                todo_assignment_form.setList_note(new ArrayList<>());
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

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();
        if (bundle != null){
            String func = bundle.getString("func");
            if(func == "create_meeting"){
                String title = bundle.getString("title");
                String location = bundle.getString("location");
                String link = bundle.getString("link");
                String time = bundle.getString("time");
                String alert = bundle.getString("alert");

                meeting m = new meeting(
                        -1,
                        time.trim(),
                        title.trim(),
                        location.trim(),
                        link.trim(),
                        alert.trim(),
                        false
                );
                m.setId(this.dataBaseHelper.addOne(m));
                setAlarm(m.getId(), m.getTime(), m.getAlert(), "Thông báo cuộc họp", "Cuộc họp " + m.getTitle() + " sẽ diễn ra", 0);
                this.meetings.add(m);
            }
            else if(func == "remove_meeting"){
                String id = bundle.getString("id");
                for (int i = 0; i < meetings.size(); i ++){
                    if (meetings.get(i).getId() == Integer.parseInt(id)){
                        this.dataBaseHelper.deleteOne(this.meetings.get(i));
                        cancelAlarm(Integer.parseInt(id));
                        this.meetings.remove(i);
                        break;
                    }
                }
            }
            else if(func == "edit_info_meeting"){
                String id = bundle.getString("id");
                String title = bundle.getString("title");
                String location = bundle.getString("location");
                String link = bundle.getString("link");
                String time = bundle.getString("time");
                String alert = bundle.getString("alert");
                String done =  bundle.getString("done");

                for (meeting m: this.meetings){
                    if (m.getId() == Integer.parseInt(id)){
                        m.setAlert(alert);
                        m.setDone(Boolean.valueOf(done));
                        m.setLink(link);
                        m.setLocation(location);
                        m.setTitle(title);
                        m.setTime(time);
                        this.dataBaseHelper.updateOne(m);
                        cancelAlarm(m.getId());
                        setAlarm(m.getId(), m.getTime(), m.getAlert(), "Thông báo cuộc họp", "Cuộc họp " + m.getTitle() + " sẽ diễn ra", 0);
                        break;
                    }
                }
            }
            else if(func.trim().equals("create_assignment")){
                ArrayList<list_check> list_check = bundle.<list_check>getParcelableArrayList("list_check");
                ArrayList<Note> list_note = bundle.<Note>getParcelableArrayList("list_note");
                Log.e("assign", String.valueOf(list_check));
                Log.e("assign", String.valueOf(list_note));
                assignment new_ass = new assignment(
                        -1,
                        bundle.getString("title"),
                        bundle.getString("time_start"),
                        bundle.getString("time_end"),
                        false
                );
                new_ass.setId(dataBaseHelper.addOne(new_ass));
                for (int i = 0; i < list_check.size(); i++){
                    if (list_note.get(i) != null && list_check.get(i).getLink() == list_note.get(i).getId()){
                        list_check.get(i).setLink(dataBaseHelper.linkNote(list_note.get(i)));
                    }
                    else{
                        list_check.get(i).setLink(-1);
                    }
                    list_check.get(i).setAssign(new_ass.getId());
                    list_check.get(i).setId(dataBaseHelper.addOne(list_check.get(i)));
                }
                new_ass.setList_checks(list_check);
                ArrayList<list_check> l = dataBaseHelper.getAllListCheck(new_ass.getId());
                for (list_check a: l){
                    Log.e("check_show", String.valueOf(a.getLink()));
                }
                setAlarm(new_ass.getId(), "00:00 " + new_ass.getTimeEnd(), "02:00", "Thông báo Công việc", "Bài tập " + new_ass.getTitle() + " sẽ kết thúc sau 2 giờ nữa", 1);
                assignments.add(new_ass);
            }
            else if(func.trim().equals("edit_assignment")){
                Log.e("id", String.valueOf(bundle.getInt("id")));
                int id = bundle.getInt("id");
                String time_start = bundle.getString("time_start");
                String time_end = bundle.getString("time_end");
                String title = bundle.getString("title");
                ArrayList<list_check> list_checks = bundle.getParcelableArrayList("list_check");
                Log.e("list_checks.get(i)", String.valueOf(list_checks));
                ArrayList<Note> list_note = bundle.getParcelableArrayList("list_note");
                Log.e("list_checks_n", String.valueOf(list_note));

                for (int idx = 0; idx < assignments.size(); idx ++){
                    Log.e("list_checks", assignments.get(idx).getId() + " " + id);
                    if(assignments.get(idx).getId() == id){
                        ArrayList<list_check> all = dataBaseHelper.getAllListCheck(assignments.get(idx).getId());
                        for (int i = 0; i < all.size(); i++){
                            if (all.get(i).getLink() != -1){
                                dataBaseHelper.deleteLinkedNote(dataBaseHelper.unlinkNote(all.get(i)));
                                all.get(i).setLink(-1);
                            }
                            dataBaseHelper.deleteOne(all.get(i));
                        }

                        Log.e("list_checks.get(i)", String.valueOf(list_checks));
                        for (int i = 0; i < list_checks.size(); i ++){
                            if (list_note.get(i) != null ){
                                list_checks.get(i).setLink(dataBaseHelper.linkNote(list_note.get(i)));
                            }
                            else{
                                list_checks.get(i).setLink(-1);
                            }
                            list_checks.get(i).setAssign(assignments.get(idx).getId());
                            dataBaseHelper.addOne(list_checks.get(i));
                            Log.e("list_checks.get(i)", String.valueOf(list_checks.get(i).getLink()));
                        }

                        ArrayList<list_check> l = dataBaseHelper.getAllListCheck(assignments.get(idx).getId());
                        for (list_check a: l){
                            Log.e("check_show", String.valueOf(a.getLink()));
                        }

                        assignments.get(idx).setTitle(title);
                        assignments.get(idx).setTimeStart(time_start);
                        assignments.get(idx).setTimeEnd(time_end);
                        assignments.get(idx).setTime(time_end);
                        Log.e("time", time_start + " " + time_end + " " + assignments.get(idx).getTime());
                        dataBaseHelper.updateOne(assignments.get(idx));
                        cancelAlarm(assignments.get(idx).getId());
                        setAlarm(assignments.get(idx).getId(), "00:00 " + assignments.get(idx).getTimeEnd(), "02:00", "Thông báo Công việc", "Bài tập " + assignments.get(idx).getTitle() + " sẽ kết thúc sau 2 giờ nữa", 1);
                        break;
                    }
                }
            }
            else if(func.trim().equals("remove_assignment")){
                int id = bundle.getInt("id");
                for (int idx = 0; idx < assignments.size(); idx ++){
                    if(assignments.get(idx).getId() == id){
                        dataBaseHelper.deleteOne(assignments.get(idx));
                        cancelAlarm(assignments.get(idx).getId());
                        assignments.remove(idx);
                        break;
                    }
                }
            }
            else if (func.trim().equals("todoOpen")){
                list_check listCheck = bundle.getParcelable("listCheck");
                Note note = bundle.getParcelable("note");
                if (note == null){
                    dataBaseHelper.deleteLinkedNote(dataBaseHelper.unlinkNote(listCheck));
                    listCheck.setLink(-1);
                }
                else{
                    dataBaseHelper.updateLinkedNote(note);
                }
            }
            else{
            }
            this.setArguments(null);
        }


        if(assignments == null && meetings == null || assignments.size() == 0 && meetings.size() == 0){
            default_todo_layout.setVisibility(VISIBLE);
        }
        else{
            default_todo_layout.setVisibility(View.GONE);
            item_show();
        }
    }
    private void updateItemInAssign(ArrayList<list_check> list_checks, ArrayList<Note> list_note, int idx){
        for (int i = 0; i < assignments.get(idx).getList_checks().size(); i++){
            if (list_checks.size() != 0 && assignments.get(idx).getList_checks().get(i).getId() == list_checks.get(i).getId()){
                if (list_checks.get(i).getLink() == -1 && assignments.get(idx).getList_checks().get(i).getLink() != -1){
                    dataBaseHelper.deleteLinkedNote(dataBaseHelper.unlinkNote(assignments.get(idx).getList_checks().get(i)));
                }
                else if (list_checks.get(i).getLink() != -1 && assignments.get(idx).getList_checks().get(i).getLink() == -1){
                    dataBaseHelper.linkNote(list_note.get(i));
                }
                else {
                    dataBaseHelper.updateLinkedNote(list_note.get(i));
                }
            }
            else{
                if (assignments.get(idx).getList_checks().get(i).getLink() != -1){
                    if(dataBaseHelper.deleteLinkedNote(dataBaseHelper.unlinkNote(assignments.get(idx).getList_checks().get(i)))){
                        dataBaseHelper.deleteOne(assignments.get(idx).getList_checks().get(i));
                    }
                }
                else{
                    dataBaseHelper.deleteOne(assignments.get(idx).getList_checks().get(i));
                }
                assignments.get(idx).getList_checks().remove(i);
                i -= 1;
            }
        }
    }

    private void close_float_button_background(){
        todo_meeting_button.setVisibility(View.GONE);
        todo_assignment_button.setVisibility(View.GONE);

        bottomNavigationView.setForeground(null);
        todo_floating_button_background.getLayoutParams().width = 170;
        todo_floating_button_background.getLayoutParams().height = -2;//wrap_content
        todo_floating_button_background.setBackgroundColor(0);

        todo_floating_button.setImageResource(R.drawable.icon_add);
    }

    private void open_float_button_background(){
        todo_meeting_button.setVisibility(VISIBLE);
        todo_assignment_button.setVisibility(VISIBLE);
        bottomNavigationView.setForeground(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.dialog, null)));


        todo_floating_button_background.getLayoutParams().width = -1;//match_parent/match_parent
        todo_floating_button_background.getLayoutParams().height = -1;
        todo_floating_button_background.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.dialog, null));

        todo_floating_button.setImageResource(R.drawable.icon_close);
    }

    @Override
    public void onCheckClick(meeting meets) {
        int i = 0;
        for (meeting m: meetings){
            if (m.getId() == meets.getId()){
                meetings.get(i).setDone(meets.getDone());
                dataBaseHelper.updateOne(meets);
                break;
            }
            i += 1;
        }
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

    @Override
    public void addListCheck(list_check listCheck) {

    }

    @Override
    public void addListCheckItem() {

    }

    @Override
    public void removeListCheckItem(list_check listCheck) {

    }

    @Override
    public void removeListCheck(list_check listCheck) {

    }

    @Override
    public void openAddListCheck() {

    }

    @Override
    public void editAssignment(assignment assign) {
        Bundle bundle = new Bundle();
        bundle.putString("func", "edit_assignment");
        bundle.putInt("_id_", assign.getId());
        Log.e("ge", String.valueOf(assign.getId()));
        Log.e("ge", String.valueOf(assign.getTitle()));
        bundle.putString("title", assign.getTitle());
        bundle.putString("time_start", assign.getTimeStart());
        bundle.putString("time_end", assign.getTimeEnd());
        bundle.putString("time_left", assign.getTime());
        bundle.putParcelableArrayList("list_checks", assign.getList_checks());
        bundle.putParcelableArrayList("list_checks_dialog", new ArrayList<>());
        bundle.putParcelableArrayList("list_note", dataBaseHelper.getOne(assign.getList_checks()));
        bundle.putParcelable("todoView", todo_meeting_form.getFragment_todo());
        bundle.putString("bundle", "edit_assignment");

        todo_assignment_form.setArguments(bundle);
        todo_assignment_form.set_id_(assign.getId());
        todo_assignment_form.setTodoView(todo_meeting_form.getFragment_todo());
        todo_assignment_form.set_bundle_("edit_assignment");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todo_assignment_form).commit();
    }

    @Override
    public void onCheckAssign(assignment assign) {
        for (int i = 0; i < assignments.size(); i++){
            if(assignments.get(i).getId() == assign.getId()){
                assignments.get(i).setDone(assign.getDone());
                dataBaseHelper.updateOne(assignments.get(i));
                for (int j = 0; j < assignments.get(i).getList_checks().size(); j ++){
                    assignments.get(i).getList_checks().get(j).setDone(assignments.get(i).getDone());
                    Log.e("check_lok", "assign " + assignments.get(i).getId() + " " + assignments.get(i).getDone() + " " + assignments.get(i).getList_checks().get(j).getContent() + " " + assignments.get(i).getList_checks().get(j).getDone());
                    dataBaseHelper.updateOne(assignments.get(i).getList_checks().get(j));
                }
                ArrayList<list_check> l = dataBaseHelper.getAllListCheck(assignments.get(i).getId());
                for (list_check a: l){
                    Log.e("check_lok", "assign " + assignments.get(i).getId() + " " + assignments.get(i).getDone() + " " + a.getContent() + " " + a.getDone());
                }
                break;
            }
        }
    }

    @Override
    public void updateDB(list_check listCheck) {
        if (listCheck.getDone() == false){
            for (assignment a: assignments){
                if (a.getId() == listCheck.getAssign()){
                    a.setDone(false);
                    dataBaseHelper.updateOne(a);
                    break;
                }
            }
        }
        else {
            for (assignment a: assignments){
                if (a.getId() == listCheck.getAssign()){
                    boolean check = true;
                    for (list_check l: a.getList_checks()){
                        if (!l.getDone()){
                            check = false;
                            break;
                        }
                    }
                    if (check){
                        a.setDone(true);
                        dataBaseHelper.updateOne(a);
                    }
                    break;
                }
            }
        }
        dataBaseHelper.updateOne(listCheck);
        item_show();
    }

    @Override
    public int linkNewNote(list_check listCheck) {
        return 0;
    }

    @Override
    public void unlinkNote(int id) {

    }

    @Override
    public void openNote(list_check listCheck, String type) {

    }

    @Override
    public void openNoteView(list_check listCheck) {
        Bundle bundle = new Bundle();
        bundle.putString("func", "todoOpen");
        bundle.putString("head_back", "Quay lại");
        bundle.putString("head_add", "Xong");
        Note note = dataBaseHelper.getNote(listCheck.getLink());
        Log.e("NOTEVIEW", listCheck.getLink() + " " + listCheck.getContent() + " " + listCheck.getId());
        bundle.putString("title", note.getTitle());
        bundle.putString("content", note.getContent());
        bundle.putParcelable("note", note);
        bundle.putParcelable("listCheck", listCheck);
        bundle.putParcelable("todoView", todo_assignment_form.getTodoView());

        AddnoteFragment addnoteFragment = new AddnoteFragment();
        addnoteFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, addnoteFragment).commit();
    }

    private void item_show(){
        todo_item_RecViewAdapter adapter = new todo_item_RecViewAdapter(getActivity(), this, createTodo(), this);
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
            int check = check(this.meetings.get(idx1).getTime().split(" ")[1], this.assignments.get(idx2).getTimeStart());
            if(check == 1){
                todo_items.add(new todo_item(null, this.meetings.get(idx1)));
                idx1 += 1;
            }
            else if(check == 2){
                todo_items.add(new todo_item(this.assignments.get(idx2), null));
                idx2 += 1;
            }
            else {
                todo_items.add(new todo_item(null, this.meetings.get(idx1)));
                idx1 += 1;
                todo_items.add(new todo_item(this.assignments.get(idx2), null));
                idx2 += 1;
            }
        }

        if(idx1 < this.meetings.size()) {
            while (idx1 < this.meetings.size()){
                todo_items.add(new todo_item(null,  this.meetings.get(idx1)));
                idx1 += 1;
            }
        }
        else if(idx2 < this.assignments.size()){
            while (idx2 < this.assignments.size()){
                todo_items.add(new todo_item(this.assignments.get(idx2), null));
                idx2 += 1;
            }
        }
        return todo_items;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public void setAlarm(int id, String date, String time, String title, String message, int type)
    {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent;
        if (type == 0){
            intent = new Intent(getActivity() , MeetingAlarmReceiver.class);
        }
        else{
            intent = new Intent(getActivity() , AssignmentAlarmReceiver.class);
        }
        intent.putExtra("titleExtra", title);
        intent.putExtra("messageExtra", message);
        pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        Date selectedDate = new Date();
        LocalTime localTime = null;
        try {
            selectedDate = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(date);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int hour = 0,minute = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            hour = localTime.get(ChronoField.HOUR_OF_DAY);
            minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(selectedDate);
        Log.e("day", ""+calendar.get(Calendar.DAY_OF_MONTH));
        Log.e("month", ""+(calendar.get(Calendar.MONTH) + 1));
        Log.e("day", ""+calendar.get(Calendar.YEAR));
        Log.e("hour", ""+calendar.get(Calendar.HOUR_OF_DAY));
        Log.e("minute", ""+calendar.get(Calendar.MINUTE));
        Log.e("notification_hour", ""+hour);
        Log.e("notification_minute", ""+minute);

        calendar.setTimeInMillis(calendar.getTimeInMillis()-hour * MILLIS_IN_AN_HOUR - minute * MILLIS_IN_AN_MINUTE);
        Log.e("calendar", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(calendar.getTime()));
        Log.e("calendar", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(calendar.getTimeInMillis())));

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Log.e("alarm","SET ALARM SUCCESSFULLY");
    }
    public void cancelAlarm(int id)
    {
        Intent intent = new Intent(getActivity() , AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        if(alarmManager == null)
        {
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Log.e("alarm", "CANCEL ALARM SUCCESSFULLY");
    }

}