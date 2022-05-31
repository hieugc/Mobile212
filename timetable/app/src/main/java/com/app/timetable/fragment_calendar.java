package com.app.timetable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class fragment_calendar extends Fragment implements Parcelable {
    public fragment_calendar(){
        subjectList = new ArrayList<>();
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
    private TextView txt_month;
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private static final long MILLIS_IN_AN_HOUR = 1000 * 60 * 60;
    private static final long MILLIS_IN_AN_MINUTE = 1000 * 60;
    private RecyclerView subjectRecyclerView, calendarRecyclerView;
    boolean tmpStudyDay[] = {false,false,false,false,false,false,false};
    private ArrayList<Subject> subjectList;
    private RecyclerView.ViewHolder viewHolder;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private FloatingActionButton floatingActionButton;
    private Button calendar_tkbbk_button, calendar_tkb_button, notification_btn;
    private RelativeLayout calendar_float_button_background, bell_popup_bg;
    private fragment_new_subject tkb_new_subject;
    private fragment_calendar_info_subject info_subject;
    private LinearLayout backward_calendar,forward_calendar, calendar_dropdown;
    private TimeTableAdapter timeTableAdapter;
    private DateAdapter dateAdapter;
    private Context context;
    private long selectedDate;
    private MaterialDatePicker.Builder pickerBuilder;
    private MaterialDatePicker<Long> picker;
    private  CalendarConstraints.Builder builder;
    private TimeTable notification_popup_timetable, delete_popup_timetable;
    private SwitchMaterial notification_switch, notification_switch_all;
    private Button btn_delete_timetable, btn_delete_all_timetable;

    private LinearLayout notificationLayout;
    private RelativeLayout delete_popup_bg;

    private NumberPicker hour_noti, minutes_noti;
    private ArrayList<Date> list = new ArrayList<>();
    private int currentposition;
    private LogInFragment logInFragment;
    private DataBaseHelper dataBaseHelper;

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
        context = calendarView.getContext();

        dataBaseHelper = new DataBaseHelper(calendarView.getContext());
        dateAdapter.setDataBaseHelper(dataBaseHelper);
        subjectRecyclerView = calendarView.findViewById(R.id.tkb_list);
        dateAdapter.setFragmentCalendar(this);
        txt_month = calendarView.findViewById(R.id.txt_month);

        btn_delete_timetable = calendarView.findViewById(R.id.btn_delete_timetable);
        btn_delete_all_timetable = calendarView.findViewById(R.id.btn_delete_all_timetable);


        delete_popup_bg = calendarView.findViewById(R.id.delete_popup_bg);
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
        int month = calendar.get(Calendar.MONTH)+1;
        txt_month.setText("Tháng "+month+"/"+year);

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

                Calendar c = Calendar.getInstance(TimeZone.getDefault());
                c.setTime(date);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                txt_month.setText("Tháng "+month+"/"+year);

                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
            }
        });


        // horizontal recyclerView for day

        calendarRecyclerView = calendarView.findViewById(R.id.calendar_recyclerview);
        calendarRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new CenterZoomLayoutManager(calendarView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        calendarRecyclerView.setLayoutManager(mLayoutManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(calendarRecyclerView);

        forward_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAdapter.getArrayList().remove(0);
                Date date = dateAdapter.getArrayList().get(dateAdapter.getItemCount()-1);
                Date nextDay = new Date(date.getTime() + MILLIS_IN_A_DAY);
                dateAdapter.getArrayList().add(nextDay);
                dateAdapter.notifyDataSetChanged();
                selectedDate = dateAdapter.getArrayList().get(2).getTime();
                Calendar c = Calendar.getInstance(TimeZone.getDefault());
                c.setTime(dateAdapter.getArrayList().get(2));
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                txt_month.setText("Tháng "+month+"/"+year);

                setDatePicker();
                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(dateAdapter.getArrayList().get(2));
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
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
                Calendar c = Calendar.getInstance(TimeZone.getDefault());
                c.setTime(dateAdapter.getArrayList().get(2));
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                txt_month.setText("Tháng "+month+"/"+year);
                setDatePicker();
                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(dateAdapter.getArrayList().get(2));
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
            }
        });

        calendarRecyclerView.setItemAnimator(new DefaultItemAnimator());

        calendarRecyclerView.setAdapter(dateAdapter);

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
                bottomNavigationView.setForeground(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.dialog, null)));
            }

            @Override
            public void onDelete(TimeTable timeTable, RecyclerView.ViewHolder holder)
            {
                if(viewHolder != null) viewHolder.itemView.scrollTo(0, 0);
                viewHolder = holder;
                delete_popup_timetable = timeTable;
                delete_popup_bg.setVisibility(VISIBLE);
                bottomNavigationView.setForeground(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.dialog, null)));
            }
        });

        timeTableAdapter.setBottomNavigationView(bottomNavigationView);


        btn_delete_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.itemView.scrollTo(0, 0);
                dataBaseHelper.deleteOne(delete_popup_timetable);
                cancelAlarm(delete_popup_timetable.getId());
                delete_popup_bg.setVisibility(GONE);
                bottomNavigationView.setForeground(null);

                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(dateAdapter.getArrayList().get(2));
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
            }
        });

        btn_delete_all_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.itemView.scrollTo(0, 0);
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimeTablesByForeignID(delete_popup_timetable);
                for(int i = 0; i < arrayList.size(); i++){
                    dataBaseHelper.deleteOne(arrayList.get(i));
                    cancelAlarm(arrayList.get(i).getId());
                }
                if(delete_popup_timetable.getType() == 1)
                {
                    dataBaseHelper.deleteSubject(delete_popup_timetable.getTimetable_id());
                }
                delete_popup_bg.setVisibility(GONE);
                bottomNavigationView.setForeground(null);
                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(dateAdapter.getArrayList().get(2));
                ArrayList<TimeTable> timeTables = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(timeTables);
                setImageView(timeTables);
            }
        });

        delete_popup_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.itemView.scrollTo(0, 0);
                delete_popup_bg.setVisibility(GONE);
                bottomNavigationView.setForeground(null);
            }
        });


        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bell_popup_bg.setVisibility(GONE);
                bottomNavigationView.setForeground(null);
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
                //Cancel Alarm
                cancelAlarm(notification_popup_timetable.getId());
                if(notification_switch.isChecked())
                {
                    String startTime = notification_popup_timetable.getStart_time();
                    if(startTime.length() == 3)
                        startTime = "0"+startTime;
                    String date = notification_popup_timetable.getDate()+" "+startTime;
                    setAlarm(notification_popup_timetable.getId(), date, notification_time, "Thông báo lịch học" ,"Đã tới giờ cho lịch học môn "+notification_popup_timetable.getName()+". Hãy nhanh chóng chuẩn bị nào");
                }
                if(notification_switch_all.isChecked())
                {
                    ArrayList<TimeTable> timeTables = dataBaseHelper.getTimeTablesByForeignID(notification_popup_timetable);
                    for(int i = 0; i < timeTables.size(); i++)
                    {
                        timeTables.get(i).setNotification(notification_switch.isChecked());
                        timeTables.get(i).setNotification_time(notification_time);
                        dataBaseHelper.updateOne(timeTables.get(i));
                        cancelAlarm(timeTables.get(i).getId());
                        if(notification_switch.isChecked())
                        {
                            String startTime = timeTables.get(i).getStart_time();
                            if(startTime.length() == 3)
                                startTime = "0"+startTime;
                            String date = timeTables.get(i).getDate()+" "+startTime;
                            setAlarm(timeTables.get(i).getId(), date, notification_time,"Thông báo lịch học", "Đã tới giờ cho lịch học môn "+timeTables.get(i).getName()+". Hãy nhanh chóng chuẩn bị nào");
                        }
                    }
                }
                notification_switch_all.setChecked(false);
            }
        });


        bell_popup_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bell_popup_bg.setVisibility(GONE);
                bottomNavigationView.setForeground(null);
                notification_switch_all.setChecked(false);
            }
        });



        String date = new SimpleDateFormat("dd/MM/yyyy").format(dateAdapter.getArrayList().get(2));

        ArrayList<TimeTable> timeTables = dataBaseHelper.getTimetableByDate(date);
        timeTableAdapter.setArrayList(timeTables);
        subjectRecyclerView.setAdapter(timeTableAdapter);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(calendarView.getContext()));
        setImageView(timeTables);

        setItemTouchHelper();

        return calendarView;
    }

    private void setItemTouchHelper() {

        new ItemTouchHelper(new ItemTouchHelper.Callback() {

            private int limitScrollX = dipToPx(65f, context);
            private int currentScrollX = 0;
            private int currentScrollXWhenInActive = 0;
            private float initXWhenInActive = 0f;
            private boolean firstInActive = false;


            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public float getSwipeEscapeVelocity(float defaultValue) {
                return (float) Integer.MAX_VALUE;
            }

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return (float) Integer.MAX_VALUE;

            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);

                if(viewHolder.itemView.getScrollX() > limitScrollX)
                {
                    viewHolder.itemView.scrollTo(limitScrollX, 0);
                }
                else if(viewHolder.itemView.getScrollX() < 0)
                {
                    viewHolder.itemView.scrollTo(0, 0);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
                {
                    if(dX == 0f)
                    {
                        currentScrollX = viewHolder.itemView.getScrollX();
                        firstInActive = true;
                    }

                    if(isCurrentlyActive)
                    {
                        int scrollOffset = currentScrollX + (int) (-dX);
                        if(scrollOffset > limitScrollX)
                        {
                            scrollOffset = limitScrollX;
                        }
                        else if(scrollOffset < 0)
                        {
                            scrollOffset = 0;
                        }

                        viewHolder.itemView.scrollTo(scrollOffset, 0);
                    }
                    else{
                        if(firstInActive)
                        {
                            firstInActive = false;
                            currentScrollXWhenInActive = viewHolder.itemView.getScrollX();
                            initXWhenInActive = dX;
                        }
                        if(viewHolder.itemView.getScrollX() < limitScrollX)
                        {
                            viewHolder.itemView.scrollTo((int)(currentScrollXWhenInActive* dX/ initXWhenInActive) , 0);
                        }
                    }
                }
            }

        }).attachToRecyclerView(subjectRecyclerView);
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
        calendar_float_button_background.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.dialog, null));

        bottomNavigationView.setForeground(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.dialog, null)));

        floatingActionButton.setImageResource(R.drawable.icon_close);
        calendar_tkb_button.setVisibility(VISIBLE);
        calendar_tkbbk_button.setVisibility(VISIBLE);
    }

    public void getSubjectToList(Subject subject){
        subjectList.add(subject);
        delete_popup_bg.setVisibility(VISIBLE);
        bottomNavigationView.setForeground(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.dialog, null)));

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

                Calendar c = Calendar.getInstance(TimeZone.getDefault());
                c.setTime(date);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                txt_month.setText("Tháng "+month+"/"+year);

                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                ArrayList<TimeTable> arrayList = dataBaseHelper.getTimetableByDate(selectedDate);
                timeTableAdapter.setArrayList(arrayList);
                setImageView(arrayList);
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

    public int dipToPx(float dp, Context context)
    {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public void setTxt_month(String text){
        txt_month.setText(text);
    }

    public void setAlarm(int id, String date, String time, String title, String message)
    {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity() , AlarmReceiver.class);
        intent.putExtra("titleExtra", title);
        intent.putExtra("messageExtra", message);

        pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Date selectedDate = new Date();
        LocalTime localTime = null;
        try {
            selectedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
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

        Calendar c = Calendar.getInstance(TimeZone.getDefault());

        if(c.getTimeInMillis() > calendar.getTimeInMillis())
            return;

        calendar.setTimeInMillis(calendar.getTimeInMillis()-hour*MILLIS_IN_AN_HOUR-minute*MILLIS_IN_AN_MINUTE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
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
    }
}
