package com.app.timetable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BkTimeTableSelectionFragment extends Fragment {

    private RecyclerView timetableView;
    private TextView cancel_txt,selection_txt;
    private BkTimeTableFragment bkTimeTableFragment = new BkTimeTableFragment();

    private CheckBox btn_select_all;

    private ArrayList<String> arrayList = new ArrayList();

    public BkTimeTableSelectionFragment(String name) {
        arrayList.add(name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bk_time_table_selection, container, false);

        timetableView = view.findViewById(R.id.bk_timetable_recyclerView_selection);
        cancel_txt = view.findViewById(R.id.cancel_txt);
        selection_txt = view.findViewById(R.id.selection_txt);
        btn_select_all = view.findViewById(R.id.btn_radio_select_all);

        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, bkTimeTableFragment).commit();
            }
        });

        ArrayList<BKTimeTable> timetable = new ArrayList<>();

        timetable.add(new BKTimeTable("Đồ án đa ngành (CO3011)", "H1-603","7:00 - 8:50", "01|02|03|04|--|--|07|08|09|--|11|12|13|14|15|16|17|18|"));
        timetable.add(new BKTimeTable("Nguyên lý ngôn ngữ lập trình (CO3005)","H6-109","9:00 - 11:50","01|02|03|04|--|--|07|08|09|--|--|--|--|14|15|16|17|18|"));

        TimeTableSelRecViewAdapter adapter = new TimeTableSelRecViewAdapter(getActivity(), arrayList);

        adapter.setListener(new TimeTableSelRecViewAdapter.OnItemsClickListener() {
            @Override
            public void onItemClick(BKTimeTable bkTimeTable) {
                selection_txt.setText("Đã chọn " + adapter.getBkTimeTableList().size() + " môn");
                Toast.makeText(view.getContext(), "Size "+adapter.getBkTimeTableList().size(), Toast.LENGTH_SHORT).show();
                btn_select_all.setChecked(adapter.getBkTimeTableList().size() == adapter.getTimetable().size());
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
                        Toast.makeText(view.getContext(), "Size "+adapter.getBkTimeTableList().size(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        return view;
    }
}