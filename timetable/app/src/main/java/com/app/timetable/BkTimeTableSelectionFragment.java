package com.app.timetable;

import android.content.DialogInterface;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class BkTimeTableSelectionFragment extends Fragment {

    private RecyclerView timetableView;
    private TextView cancel_txt,selection_txt,add_txt;
    private int userId;
    private BkTimeTableFragment bkTimeTableFragment;
    private CheckBox btn_select_all;
    private DataBaseHelper dataBaseHelper;

    private ArrayList<String> arrayList = new ArrayList();

    public BkTimeTableSelectionFragment(String name, int userId) {
        arrayList.add(name);
        this.userId = userId;
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
                bkTimeTableFragment = new BkTimeTableFragment(userId);
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
                    }
                });
                builder.show();
            }
        });

        ArrayList<BKTimeTable> timetable = dataBaseHelper.getTimeTable(userId);

//        timetable.add(new BKTimeTable(-1,"Đồ án đa ngành (CO3011)", "H1-603","Thứ --","7:00 - 8:50", "01|02|03|04|--|--|07|08|09|--|11|12|13|14|15|16|17|18|","212",1));
//        timetable.add(new BKTimeTable(-1,"Nguyên lý ngôn ngữ lập trình (CO3005)","H6-109","Thứ 4","9:00 - 11:50","01|02|03|04|--|--|07|08|09|--|--|--|--|14|15|16|17|18|","212",1));

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