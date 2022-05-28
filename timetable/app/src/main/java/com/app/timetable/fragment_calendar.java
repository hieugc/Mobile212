package com.app.timetable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import androidx.recyclerview.widget.SnapHelper;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class fragment_calendar extends Fragment implements Parcelable {
    public fragment_calendar(){
        subjectList = new ArrayList<>();
        subjectList.add(new Subject("Đại số tuyến tính","H6 305","L01","","17/03/2022",
                "20/06/2022","9:00","10:50",tmpStudyDay,"",""
                ,""));
        selectedDate = MaterialDatePicker.todayInUtcMilliseconds();
        dateAdapter = new DateAdapter();
        ArrayList<Date> dates = new ArrayList<>();
        Date date = new Date();
        Date yesterday = new Date(date.getTime() - MILLIS_IN_A_DAY);
        Date thePreviousDay = new Date(date.getTime() - 2*MILLIS_IN_A_DAY);
        Date tomorrow = new Date(date.getTime() + MILLIS_IN_A_DAY);
        Date theNextDay = new Date(date.getTime() + 2*MILLIS_IN_A_DAY);
        dates.add(thePreviousDay);
        dates.add(yesterday);
        dates.add(date);
        dates.add(tomorrow);
        dates.add(theNextDay);
        dateAdapter.setArrayList(dates);
    }

    private BottomNavigationView bottomNavigationView;

    protected fragment_calendar(Parcel in) {
        tmpStudyDay = in.createBooleanArray();
        selectedDate = in.readLong();
        currentposition = in.readInt();
    }

    public static final Creator<fragment_calendar> CREATOR = new Creator<fragment_calendar>() {
        @Override
        public fragment_calendar createFromParcel(Parcel in) {
            return new fragment_calendar(in);
        }

        @Override
        public fragment_calendar[] newArray(int size) {
            return new fragment_calendar[size];
        }
    };

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }
    private ImageView imageView;
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private RecyclerView subjectRecyclerView, calendarRecyclerView;
    private SubjectAdapter adapter;
    boolean tmpStudyDay[] = {false,false,false,false,false,false,false};
    private ArrayList<Subject> subjectList;

    private FloatingActionButton floatingActionButton;
    private Button calendar_tkbbk_button, calendar_tkb_button, notification_btn;
    private RelativeLayout calendar_float_button_background, bell_popup_bg;
    private fragment_new_subject tkb_new_subject;
    private fragment_calendar_info_subject info_subject;
    private LinearLayout backward_calendar,forward_calendar, calendar_dropdown;
    private TimeTableAdapter timeTableAdapter;
    private DateAdapter dateAdapter;
    private long selectedDate;
    private MaterialDatePicker.Builder pickerBuilder;
    private MaterialDatePicker<Long> picker;
    private  CalendarConstraints.Builder builder;
    private TimeTable notification_popup_timetable;
    private SwitchMaterial notification_switch, notification_switch_all;

    private LinearLayout add_subject_success, notificationLayout;

    private NumberPicker hour_noti, minutes_noti;
    private ArrayList<MyCalendar> calendarList= new ArrayList<>();
    private ArrayList<Date> list = new ArrayList<>();
    private CalendarAdapter mAdapter;
    private int currentposition;
    private LogInFragment logInFragment;
    private DataBaseHelper dataBaseHelper;



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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBooleanArray(tmpStudyDay);
        parcel.writeLong(selectedDate);
        parcel.writeInt(currentposition);
    }

    public interface ISendDataListener {
        void sendData(Subject subject);
    }

    public void setSelectedDate(long selectedDate) {
        this.selectedDate = selectedDate;
    }

    public TimeTableAdapter getTimeTableAdapter() {
        return timeTableAdapter;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        mISendDataListener = (ISendDataListener) getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this
        bottomNavigationView.setForeground(null);
        View calendarView = inflater.inflate(R.layout.fragment_calendar, container, false);

        dataBaseHelper = new DataBaseHelper(calendarView.getContext());
        dateAdapter.setDataBaseHelper(dataBaseHelper);
        subjectRecyclerView = (RecyclerView) calendarView.findViewById(R.id.tkb_list);
        dateAdapter.setFragmentCalendar(this);

        add_subject_success = calendarView.findViewById(R.id.add_subject_successfully);
        bell_popup_bg = calendarView.findViewById(R.id.calendar_popup_bg);
        notification_btn = calendarView.findViewById(R.id.done_add_time_study);
        notificationLayout = calendarView.findViewById(R.id.notificationLayout);

        notification_switch = calendarView.findViewById(R.id.notification_switch);
        notification_switch_all = calendarView.findViewById(R.id.notification_switch_all);


        floatingActionButton = calendarView.findViewById(R.id.calendar_float_button);
        calendar_tkb_button = calendarView.findViewById(R.id.calendar_tkb_button);
        calendar_tkbbk_button = calendarView.findViewById(R.id.calendar_tkbbk_button);
        calendar_float_button_background = calendarView.findViewById(R.id.calendar_float_button_background);

        hour_noti = calendarView.findViewById(R.id.calendar_hour_notification);
        minutes_noti = calendarView.findViewById(R.id.calendar_minutes_notification);

        backward_calendar = calendarView.findViewById(R.id.backward_calendar);
        forward_calendar = calendarView.findViewById(R.id.forward_calendar);
        calendar_dropdown = calendarView.findViewById(R.id.calendar_dropdown);

        imageView = calendarView.findViewById(R.id.image_default);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);

        calendar.set(Calendar.YEAR, year -1 );
        long lastYear = calendar.getTimeInMillis();

        calendar.set(Calendar.YEAR, year + 1);
        long nextYear = calendar.getTimeInMillis();

        builder = new CalendarConstraints.Builder();
        builder.setStart(lastYear);
        builder.setEnd(nextYear);
        builder.setOpenAt(selectedDate);

        pickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày")
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .setSelection(selectedDate)
                .setCalendarConstraints(builder.build());
        picker = pickerBuilder.build();


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
                logInFragment = new LogInFragment(fragment_calendar.this);
                logInFragment.setBottomNavigationView(bottomNavigationView);
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

        calendar_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show(getActivity().getSupportFragmentManager(), "CALENDAR_DATE_PICKER");
            }
        });

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                selectedDate = selection;
                Date date = new Date(selection);
                Date oneDayBefore = new Date(date.getTime() - MILLIS_IN_A_DAY);
                Date twoDayBefore = new Date(date.getTime() - 2 * MILLIS_IN_A_DAY);
                Date oneDayAfter = new Date(date.getTime() + MILLIS_IN_A_DAY);
                Date twoDayAfter = new Date(date.getTime() + 2 * MILLIS_IN_A_DAY);

                ArrayList<Date> dateArrayList = new ArrayList<>();
                dateArrayList.add(twoDayBefore);
                dateArrayList.add(oneDayBefore);
                dateArrayList.add(date);
                dateArrayList.add(oneDayAfter);
                dateArrayList.add(twoDayAfter);

                dateAdapter.getArrayList().clear();
                dateAdapter.setArrayList(dateArrayList);

                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
                Log.e("date", new SimpleDateFormat("dd/MM/yyyy").format(date));
            }
        });


        // horizontal recyclerView for day

        calendarRecyclerView = calendarView.findViewById(R.id.calendar_recyclerview);
        calendarRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new CenterZoomLayoutManager(calendarView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        calendarRecyclerView.setLayoutManager(mLayoutManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(calendarRecyclerView);

        //mAdapter = new CalendarAdapter(calendarList);


//        calendarRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView,
//                                   int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int totalItemCount = mLayoutManager.getChildCount();
//                for (int i = 0; i < totalItemCount; i++){
//                    View childView = recyclerView.getChildAt(i);
//                    TextView childTextView = (TextView) (childView.findViewById(R.id.calendar_day));
//                    String childTextViewText = (String) (childTextView.getText());
//                    if (childTextViewText.equals("Sun"))
//                        childTextView.setTextColor(Color.RED);
//                    else
//                        childTextView.setTextColor(Color.BLACK);
//                }
//
//
//            }
//        });

        forward_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAdapter.getArrayList().remove(0);
                Date date = dateAdapter.getArrayList().get(dateAdapter.getItemCount()-1);
                Date nextDay = new Date(date.getTime() + MILLIS_IN_A_DAY);
                dateAdapter.getArrayList().add(nextDay);
                dateAdapter.notifyDataSetChanged();
                selectedDate = dateAdapter.getArrayList().get(2).getTime();
                setDatePicker();
                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(dateAdapter.getArrayList().get(2));
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
                Log.e("selected", String.valueOf(selectedDate));
            }
        });
        backward_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAdapter.getArrayList().remove(dateAdapter.getItemCount()-1);
                Date date = dateAdapter.getArrayList().get(0);
                Date previousDay = new Date(date.getTime() - MILLIS_IN_A_DAY);
                dateAdapter.getArrayList().add(0, previousDay);
                dateAdapter.notifyDataSetChanged();
                selectedDate = dateAdapter.getArrayList().get(2).getTime();
                setDatePicker();
                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(dateAdapter.getArrayList().get(2));
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
                Log.e("selected", String.valueOf(selectedDate));
            }
        });

//        calendarRecyclerView.addItemDecoration(new DividerItemDecoration(calendarView.getContext(), LinearLayoutManager.HORIZONTAL));

        calendarRecyclerView.setItemAnimator(new DefaultItemAnimator());

        calendarRecyclerView.setAdapter(dateAdapter);
//        calendarRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(calendarView.getContext(), calendarRecyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                MyCalendar calendar = calendarList.get(position);
//                TextView childTextView = (TextView) (view.findViewById(R.id.calendar_day));
//
//                Animation startRotateAnimation = AnimationUtils.makeInChildBottomAnimation(calendarView.getContext());
//                childTextView.startAnimation(startRotateAnimation);
//                childTextView.setTextColor(Color.CYAN);
//                Toast.makeText(calendarView.getContext(), calendar.getDay() + " is selected!", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                MyCalendar calendar = calendarList.get(position);
//
//                TextView childTextView = (TextView) (view.findViewById(R.id.calendar_day));
//                childTextView.setTextColor(Color.GREEN);
//
//                Toast.makeText(calendarView.getContext(), calendar.getDate()+"/" + calendar.getDay()+"/" +calendar.getMonth()+"   selected!", Toast.LENGTH_SHORT).show();
//
//            }
//        }));

//        prepareCalendarData();

        // recyclerView for subject item

        timeTableAdapter = new TimeTableAdapter(getActivity(), calendarView.getContext(), this, new TimeTableAdapter.onItemClick() {
            @Override
            public void onClick(TimeTable timeTable) {
                notification_popup_timetable = timeTable;
                notification_switch.setChecked(timeTable.getNotification());
                String[] notification_time = timeTable.getNotification_time().split(":");
                int hour = Integer.parseInt(notification_time[0]);
                int minute = Integer.parseInt(notification_time[1]);
                hour_noti.setValue(hour);
                minutes_noti.setValue(minute);
                bell_popup_bg.setVisibility(VISIBLE);
                Log.e("timetable", timeTable.toString());
            }
        });
        timeTableAdapter.setBottomNavigationView(bottomNavigationView);

        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bell_popup_bg.setVisibility(GONE);
                Log.e("noti", ""+notification_switch.isChecked());
                Log.e("all", ""+notification_switch_all.isChecked());
                String hour = hour_noti.getValue()+"";
                String minute = minutes_noti.getValue()+"";
                if (hour.length() == 1) {
                    hour = "0" + hour;
                }
                if (minute.length() == 1){
                    minute = "0" + minute;
                }
                String notification_time = hour+":"+minute;
                notification_popup_timetable.setNotification(notification_switch.isChecked());
                notification_popup_timetable.setNotification_time(notification_time);
                dataBaseHelper.updateOne(notification_popup_timetable);

                if(notification_switch_all.isChecked())
                {
                    ArrayList<TimeTable> timeTables = dataBaseHelper.getTimeTablesByForeignID(notification_popup_timetable.getTimetable_id());
                    for(int i = 0; i < timeTables.size(); i++)
                    {
                        timeTables.get(i).setNotification(notification_switch.isChecked());
                        timeTables.get(i).setNotification_time(notification_time);
                        dataBaseHelper.updateOne(timeTables.get(i));
                    }
                }
                Log.e("button", notification_time);
                notification_switch_all.setChecked(false);
            }
        });

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("layout", "hey");
            }
        });

        bell_popup_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bell_popup_bg.setVisibility(GONE);
                notification_switch_all.setChecked(false);
                Log.e("bg", "hey");
            }
        });



        String date = new SimpleDateFormat("dd/MM/yyyy").format(dateAdapter.getArrayList().get(2));

        ArrayList<TimeTable> timeTables = dataBaseHelper.getTimetableByDate(date);
        timeTableAdapter.setArrayList(timeTables);
        subjectRecyclerView.setAdapter(timeTableAdapter);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(calendarView.getContext()));
        setImageView(timeTables);

        Log.e("timetable", timeTables.toString());
//        timeTables.add(new TimeTable(-1, "Đại số tuyến tính", "L01", "H6-109", "05/01/2022","9:00","10:50", 1, 1));
//        timeTables.add(new TimeTable(-1, "Đại số tuyến tính", "L01", "H6-109", "05/01/2022","9:00","10:50", 1, 1));
//        timeTables.add(new TimeTable(-1, "Đại số tuyến tính", "L01", "H6-109", "05/01/2022","9:00","10:50", 1, 1));

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

        bottomNavigationView.setForeground(null);

        calendar_tkb_button.setVisibility(View.GONE);
        calendar_tkbbk_button.setVisibility(View.GONE);
    }

    private void open_float_button_background(){
        calendar_float_button_background.getLayoutParams().width = -1;//match_parent/
        calendar_float_button_background.getLayoutParams().height = -1;
        calendar_float_button_background.setBackgroundColor(Color.parseColor("#52000000"));


        bottomNavigationView.setForeground(new ColorDrawable(Color.parseColor("#52000000")));

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

    public void setDatePicker()
    {
        builder.setOpenAt(selectedDate);
        picker = pickerBuilder.setSelection(selectedDate).setCalendarConstraints(builder.build()).build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                selectedDate = selection;
                Date date = new Date(selection);
                Date oneDayBefore = new Date(date.getTime() - MILLIS_IN_A_DAY);
                Date twoDayBefore = new Date(date.getTime() - 2 * MILLIS_IN_A_DAY);
                Date oneDayAfter = new Date(date.getTime() + MILLIS_IN_A_DAY);
                Date twoDayAfter = new Date(date.getTime() + 2 * MILLIS_IN_A_DAY);

                ArrayList<Date> dateArrayList = new ArrayList<>();
                dateArrayList.add(twoDayBefore);
                dateArrayList.add(oneDayBefore);
                dateArrayList.add(date);
                dateArrayList.add(oneDayAfter);
                dateArrayList.add(twoDayAfter);

                dateAdapter.getArrayList().clear();
                dateAdapter.setArrayList(dateArrayList);

                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
                Log.e("date", new SimpleDateFormat("dd/MM/yyyy").format(date));
            }
        });
    }

    public void setImageView(ArrayList<TimeTable> timeTables){
        if (timeTables.size() == 0){
            imageView.setVisibility(VISIBLE);
        }
        else{
            imageView.setVisibility(GONE);
        }
    }

}
