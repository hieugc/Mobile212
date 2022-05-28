package com.app.timetable;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class BkTimeTableSelectionFragment extends Fragment {

    private RecyclerView timetableView;
    private TextView cancel_txt,selection_txt,add_txt;
    private int userId;
    private BkTimeTableFragment bkTimeTableFragment;
    private CheckBox btn_select_all;
    private DataBaseHelper dataBaseHelper;
    private fragment_calendar fragmentCalendar;

    private ArrayList<String> arrayList = new ArrayList();

    public BkTimeTableSelectionFragment(String name, int userId) {
        arrayList.add(name);
        this.userId = userId;
    }

    public void setFragmentCalendar(fragment_calendar fragmentCalendar) {
        this.fragmentCalendar = fragmentCalendar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bk_time_table_selection, container, false);
        dataBaseHelper = new DataBaseHelper(view.getContext());

        timetableView = view.findViewById(R.id.bk_timetable_recyclerView_selection);
        cancel_txt = view.findViewById(R.id.cancel_txt);
        selection_txt = view.findViewById(R.id.selection_txt);
        add_txt = view.findViewById(R.id.add_txt);
        btn_select_all = view.findViewById(R.id.btn_radio_select_all);

        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bkTimeTableFragment = new BkTimeTableFragment(userId, fragmentCalendar);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, bkTimeTableFragment).commit();
            }
        });

        add_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext(), R.style.RoundShapeTheme);
                builder.setTitle("Xác nhận thêm môn?");
                builder.setMessage("Bạn muốn thêm các môn này vào thời khoá biểu?");
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Huỷ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), arrayList.toString(), Toast.LENGTH_SHORT).show();
                        ArrayList<BKTimeTable> bkTimeTables = new ArrayList<>();
                        for(int j = 0; j < arrayList.size(); j++)
                        {
                            bkTimeTables.add(dataBaseHelper.getTimeTable(userId, arrayList.get(j)));
                        }

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                        Calendar calendarForSunday = Calendar.getInstance(TimeZone.getDefault());
                        int date = 0;

                        for(int j = 0; j < bkTimeTables.size(); j++){
                            String time = bkTimeTables.get(j).getTime();
                            String name = bkTimeTables.get(j).getName();
                            String group = bkTimeTables.get(j).getGroup();
                            String location = bkTimeTables.get(j).getLocation();
                            String[] result = time.split(" - ");
                            String startTime = result[0];
                            String endTime = result[1];
                            if(startTime.length() == 3)
                            {
                                startTime = "0"+startTime;
                            }
                            if(endTime.length() == 3)
                            {
                                endTime = "0"+endTime;
                            }
                            String theDate;
                            int timetable_id = bkTimeTables.get(j).getId();
                            String[] week = bkTimeTables.get(j).getWeek().split("\\|");
                            String dateOfWeek = bkTimeTables.get(j).getDate();
                            switch (dateOfWeek)
                            {
                                case "Thứ --":
                                    date = 8;
                                    break;
                                case "Thứ 2":
                                    date = Calendar.MONDAY;
                                    break;
                                case "Thứ 3":
                                    date = Calendar.TUESDAY;
                                    break;
                                case "Thứ 4":
                                    date = Calendar.WEDNESDAY;
                                    break;
                                case "Thứ 5":
                                    date = Calendar.THURSDAY;
                                    break;
                                case "Thứ 6":
                                    date = Calendar.FRIDAY;
                                    break;
                                case "Thứ 7":
                                    date = Calendar.SATURDAY;
                                    break;
                                case "Thứ 8":
                                    date = Calendar.SUNDAY;
                                    break;
                            }

                            for (String s : week) {
                                if (s.equals("--")) continue;
                                calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(s) + 1);
                                if (date == 8) {
                                    for (int x = Calendar.SUNDAY; x <= Calendar.SATURDAY; x++) {
                                        if (x == Calendar.SUNDAY) {
                                            calendarForSunday.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(s) + 2);
                                            calendarForSunday.set(Calendar.DAY_OF_WEEK, x);
                                            theDate = format.format(calendarForSunday.getTime());
                                            Log.e("date", format.format(calendarForSunday.getTime()));
                                            dataBaseHelper.addOne(new TimeTable(-1, name, group, location, theDate, startTime, endTime, 2, timetable_id));
                                            continue;
                                        }
                                        calendar.set(Calendar.DAY_OF_WEEK, x);
                                        Log.e("date", format.format(calendar.getTime()));
                                        theDate = format.format(calendar.getTime());
                                        dataBaseHelper.addOne(new TimeTable(-1, name, group, location, theDate, startTime, endTime, 2, timetable_id));
                                    }
                                } else if (date != 0) {
                                    if (date == Calendar.SUNDAY) {
                                        calendarForSunday.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(s) + 2);
                                        calendarForSunday.set(Calendar.DAY_OF_WEEK, date);
                                        Log.e("date", format.format(calendarForSunday.getTime()));
                                        theDate = format.format(calendarForSunday.getTime());
                                        dataBaseHelper.addOne(new TimeTable(-1, name, group, location, theDate, startTime, endTime, 2, timetable_id));
                                    } else {
                                        calendar.set(Calendar.DAY_OF_WEEK, date);
                                        Log.e("date", format.format(calendar.getTime()));
                                        theDate = format.format(calendar.getTime());
                                        dataBaseHelper.addOne(new TimeTable(-1, name, group, location, theDate, startTime, endTime, 2, timetable_id));
                                    }
                                }
                            }

                        }
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragmentCalendar).commit();
                    }
                });
                builder.show();
            }
        });

        ArrayList<BKTimeTable> timetable = dataBaseHelper.getTimeTable(userId);


        TimeTableSelRecViewAdapter adapter = new TimeTableSelRecViewAdapter(getActivity(), arrayList);

        adapter.setListener(new TimeTableSelRecViewAdapter.OnItemsClickListener() {
            @Override
            public void onItemClick(BKTimeTable bkTimeTable) {
                selection_txt.setText("Đã chọn " + adapter.getBkTimeTableList().size() + " môn");
                Toast.makeText(view.getContext(), "Size "+adapter.getBkTimeTableList().size(), Toast.LENGTH_SHORT).show();
                btn_select_all.setChecked(adapter.getBkTimeTableList().size() == adapter.getTimetable().size());
                if(adapter.getBkTimeTableList().size() == 0)
                    add_txt.setVisibility(View.INVISIBLE);
                else
                    add_txt.setVisibility(View.VISIBLE);
            }
        });

        adapter.setTimetable(timetable);

        timetableView.setAdapter(adapter);
        timetableView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        selection_txt.setText("Đã chọn "+adapter.getBkTimeTableList().size()+" môn");


        btn_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    arrayList = new ArrayList<>();
                    for(int i = 0; i < adapter.getItemCount(); i++)
                    {
                        arrayList.add(adapter.getTimetable().get(i).getName());
                    }
                    adapter.setList(arrayList);
                    timetableView.setAdapter(adapter);
                    add_txt.setVisibility(View.VISIBLE);
                    selection_txt.setText("Đã chọn "+adapter.getBkTimeTableList().size()+" môn");
                    Toast.makeText(view.getContext(), "Size "+adapter.getBkTimeTableList().size(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(adapter.getBkTimeTableList().size() == 0 || adapter.getBkTimeTableList().size()==adapter.getItemCount())
                    {
                        arrayList = new ArrayList();
                        adapter.setList(arrayList);
                        timetableView.setAdapter(adapter);
                        selection_txt.setText("Đã chọn "+arrayList.size()+" môn");
                        add_txt.setVisibility(View.INVISIBLE);
                        Toast.makeText(view.getContext(), "Size "+adapter.getBkTimeTableList().size(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
}