package com.app.timetable;

import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class fragment_setting extends Fragment {
    public fragment_setting(){}
    RelativeLayout layout4;
    private CheckBox checkBox;
    private SharedPreferences preferences;
    private RelativeLayout user_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 =  inflater.inflate(R.layout.fragment_setting, container, false);
        preferences = getActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        user_layout = view1.findViewById(R.id.layout3);
        layout4 = view1.findViewById(R.id.layout4);

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(),OnBoardingActivity.class);
                startActivity(intent);
            }
        });
        user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext(), R.style.RoundShapeTheme);
                builder.setTitle("Xoá liên kết tài khoản?");
                builder.setMessage("Bạn chắc chắn muốn xoá liên kết tài khoản Bách Khoa hiện tại?");
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Huỷ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Xoá liên kết thành công", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("username");
                        editor.remove("password");
                        editor.commit();
                    }
                });
                builder.show();
            }
        });

        return view1;
    }

}
