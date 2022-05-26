package com.app.timetable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.SnapHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class fragment_calendar extends Fragment {
    public fragment_calendar(){
        subjectList = new ArrayList<>();
        subjectList.add(new Subject("Đại số tuyến tính","H6 305","L01","","17/03/2022",
                "20/06/2022","9:00","10:50",tmpStudyDay,"",""
                ,""));
    }

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    private RecyclerView subjectRecyclerView, calendarRecyclerView;
    private SubjectAdapter adapter;
    boolean tmpStudyDay[] = {false,false,false,false,false,false,false};
    private ArrayList<Subject> subjectList;
    private FloatingActionButton floatingActionButton;
    private Button calendar_tkbbk_button, calendar_tkb_button;
    private RelativeLayout calendar_float_button_background;
    private fragment_new_subject tkb_new_subject;
    private fragment_calendar_info_subject info_subject;
    private LinearLayout backward_calendar,forward_calendar;

    private LinearLayout add_subject_success;

    private NumberPicker hour_noti, minutes_noti;
    private List<MyCalendar> calendarList= new ArrayList<>();
    private CalendarAdapter mAdapter;
    private int currentposition;
    private LogInFragment logInFragment;



//        subjectList.add(new Subject("Giải tích 2","7:00","9:50",""));
//        subjectList.add(new Subject("Đại số tuyến tính","7:00","9:50",""));
//        subjectList.add(new Subject("Đại số tuyến tính","H6 305","L01","","17/03/2022","20/06/2022","9:00","10:50",tmpStudyDay,"","",""));
//        subjects.add(new Subject("Giáo dục thể chất","10:00","11:50","Note.."));
//        subjects.add(new Subject("Hệ thống số","7:00","9:50",""));
//        subjects.add(new Subject("Hệ thống số (Lab)","7:00","9:50",""));
//        subjects.add(new Subject("Hệ thống số (Lab)","7:00","9:50",""));
//        subjects.add(new Subject("Hệ thống số (Lab)","7:00","9:50",""));

    public void set_info_subject(fragment_calendar_info_subject info_subject){
        this.info_subject = info_subject;
    }
    public void set_new_tkb(fragment_new_subject form) {
        tkb_new_subject = form;
    }
    public void set_new_tkbbk(LogInFragment logInFragment) {
        this.logInFragment = logInFragment;
    }

    private ISendDataListener mISendDataListener;
    public interface ISendDataListener {
        void sendData(Subject subject);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        mISendDataListener = (ISendDataListener) getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View calendarView = inflater.inflate(R.layout.fragment_calendar, container, false);
        subjectRecyclerView = (RecyclerView) calendarView.findViewById(R.id.tkb_list);

        add_subject_success = calendarView.findViewById(R.id.add_subject_successfully);

        floatingActionButton = calendarView.findViewById(R.id.calendar_float_button);
        calendar_tkb_button = calendarView.findViewById(R.id.calendar_tkb_button);
        calendar_tkbbk_button = calendarView.findViewById(R.id.calendar_tkbbk_button);
        calendar_float_button_background = calendarView.findViewById(R.id.calendar_float_button_background);

        hour_noti = calendarView.findViewById(R.id.calendar_hour_notification);
        minutes_noti = calendarView.findViewById(R.id.calendar_minutes_notification);

        backward_calendar = calendarView.findViewById(R.id.backward_calendar);
        forward_calendar = calendarView.findViewById(R.id.forward_calendar);


        setMin_Max(hour_noti,0,23);
        setMin_Max(minutes_noti,0,59);

        calendar_tkb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,tkb_new_subject).commit();
            }
        });

        calendar_tkbbk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInFragment = new LogInFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, logInFragment).commit();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (calendar_tkbbk_button.getVisibility() == GONE){
                    open_float_button_background();
                }
                else{
                    close_float_button_background();
                }
            }
        });

        calendar_float_button_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendar_tkbbk_button.getVisibility() == VISIBLE){
                    close_float_button_background();
                }
            }
        });


        // horizontal recyclerView for day
        calendarRecyclerView = (RecyclerView) calendarView.findViewById(R.id.calendar_recyclerview);
        calendarRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new CenterZoomLayoutManager(calendarView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        calendarRecyclerView.setLayoutManager(mLayoutManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(calendarRecyclerView);

        mAdapter = new CalendarAdapter(calendarList);

        calendarRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mLayoutManager.getChildCount();
                for (int i = 0; i < totalItemCount; i++){
                    View childView = recyclerView.getChildAt(i);
                    TextView childTextView = (TextView) (childView.findViewById(R.id.calendar_day));
                    String childTextViewText = (String) (childTextView.getText());
                    if (childTextViewText.equals("Sun"))
                        childTextView.setTextColor(Color.RED);
                    else
                        childTextView.setTextColor(Color.BLACK);
                }


            }
        });

        forward_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentposition = getCurrentItem();
                int bottom = calendarRecyclerView.getAdapter().getItemCount()-1;
                if (bottom-currentposition >1)
                {
                    setCurrentItem(currentposition, 2);
                }
            }
        });
        backward_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentposition = getCurrentItem();
                setCurrentItem(currentposition-4, 0);
            }
        });

//        calendarRecyclerView.addItemDecoration(new DividerItemDecoration(calendarView.getContext(), LinearLayoutManager.HORIZONTAL));

        calendarRecyclerView.setItemAnimator(new DefaultItemAnimator());

        calendarRecyclerView.setAdapter(mAdapter);
        calendarRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(calendarView.getContext(), calendarRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);
                TextView childTextView = (TextView) (view.findViewById(R.id.calendar_day));

                Animation startRotateAnimation = AnimationUtils.makeInChildBottomAnimation(calendarView.getContext());
                childTextView.startAnimation(startRotateAnimation);
                childTextView.setTextColor(Color.CYAN);
                Toast.makeText(calendarView.getContext(), calendar.getDay() + " is selected!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);

                TextView childTextView = (TextView) (view.findViewById(R.id.calendar_day));
                childTextView.setTextColor(Color.GREEN);

                Toast.makeText(calendarView.getContext(), calendar.getDate()+"/" + calendar.getDay()+"/" +calendar.getMonth()+"   selected!", Toast.LENGTH_SHORT).show();

            }
        }));

        prepareCalendarData();


        // recyclerView for subject item

        adapter = new SubjectAdapter(subjectList,calendarView.getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(calendarView.getContext());

        subjectRecyclerView.setAdapter(adapter);
        subjectRecyclerView.setLayoutManager(linearLayoutManager);
        subjectRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(calendarView.getContext(), subjectRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Subject subject = subjectList.get(position);
                sendDataToSubjectInfo(subject);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,info_subject).commit();
            }
            @Override
            public void onLongClick(View view, int position) {

            }


        }));
        return calendarView;
    }

    private int getSnapPostion(SnapHelper snap, RecyclerView.LayoutManager layoutManager){
        View snapView = snap.findSnapView(layoutManager);
        int snapPos = layoutManager.getPosition(snapView);
        return snapPos;
    }

    private int getCurrentItem(){
        return ((LinearLayoutManager)calendarRecyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
    }

    private void setCurrentItem(int position, int incr){
        position=position+incr;

        if (position <0)
            position=0;

        calendarRecyclerView.smoothScrollToPosition(position);


    }

    private void setTest(){
        calendarRecyclerView.smoothScrollToPosition(6);
    }

    private void prepareCalendarData() {

        // run a for loop for all the next 30 days of the current month starting today
        // initialize mycalendarData and get Instance
        // getnext to get next set of date etc.. which can be used to populate MyCalendarList in for loop

        myCalendarData m_calendar = new myCalendarData(-2);

        for ( int i=0; i <30; i++) {

            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(), String.valueOf(m_calendar.getDay()), String.valueOf(m_calendar.getMonth()), String.valueOf(m_calendar.getYear()),i);
            m_calendar.getNextWeekDay(1);

            calendarList.add(calendar);

        }
        // notify adapter about data set changes
        // so that it will render the list with new data

        mAdapter.notifyDataSetChanged();
    }

    private void setMin_Max(NumberPicker picker, int min, int max){
        picker.setMinValue(min);
        picker.setMaxValue(max);
        picker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
    }

    private void close_float_button_background(){
        calendar_float_button_background.getLayoutParams().width = -2;//wrap_content
        calendar_float_button_background.getLayoutParams().height = -2;//wrap_content
        calendar_float_button_background.setBackgroundColor(0);

        floatingActionButton.setImageResource(R.drawable.icon_add);

        calendar_tkb_button.setVisibility(View.GONE);
        calendar_tkbbk_button.setVisibility(View.GONE);
    }

    private void open_float_button_background(){
        calendar_float_button_background.getLayoutParams().width = -1;//match_parent/
        calendar_float_button_background.getLayoutParams().height = -1;
        calendar_float_button_background.setBackgroundColor(Color.parseColor("#CC333333"));

        floatingActionButton.setImageResource(R.drawable.icon_close);

        calendar_tkb_button.setVisibility(VISIBLE);
        calendar_tkbbk_button.setVisibility(VISIBLE);
    }

    private void sendDataToSubjectInfo(Subject subject){
        mISendDataListener.sendData(subject);
    }

    public void getSubjectToList(Subject subject){
        Log.d("test functionality",adapter.getItemCount()+"");
        subjectList.add(subject);
        adapter.setSubjectList(subjectList);
        Log.d("test func",subjectList.toString());
        Log.d("test func2",adapter.getSubjectList().toString());
        Log.d("test functionality",adapter.getItemCount()+"");
        add_subject_success.setVisibility(VISIBLE);
    }

}
