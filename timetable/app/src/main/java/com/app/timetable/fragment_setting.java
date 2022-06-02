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
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;



public class fragment_setting extends Fragment {

    public fragment_setting(){}
    private RelativeLayout layout2, layout4, layout5;
    private CheckBox checkBox;
    private SharedPreferences preferences;
    private RelativeLayout user_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 =  inflater.inflate(R.layout.fragment_setting, container, false);
        preferences = getActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE);

        boolean notification = preferences.getBoolean("notification_bar", true);

        checkBox = view1.findViewById(R.id.checkbox1);
        checkBox.setChecked(notification);

        layout2 = view1.findViewById(R.id.layout2);
        user_layout = view1.findViewById(R.id.layout3);
        layout4 = view1.findViewById(R.id.layout4);
        layout5 = view1.findViewById(R.id.layout5);

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(),OnBoardingActivity.class);
                startActivity(intent);
            }
        });
        
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view1.getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutFragment aboutFragment = new AboutFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, aboutFragment).commit();
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE).edit();
                editor.putBoolean("notification_bar", b);
                editor.commit();
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
                    }
                });
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view1.getContext(), "Xoá liên kết thành công", Toast.LENGTH_SHORT).show();
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
