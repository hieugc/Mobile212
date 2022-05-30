package com.app.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigInteger;

public class LogInFragment extends Fragment {


    private TextInputEditText username_txt, password_txt;
    private TextInputLayout username_layout, password_layout;
    private Button btn_login, btn_clear;
    private BkTimeTableFragment bkTimeTableFragment;
    private DataBaseHelper dataBaseHelper;
    private SharedPreferences sharedPreferences;
    private String username, password;
    private BottomNavigationView bottomNavigationView;
    private fragment_calendar fragmentCalendar;

    public LogInFragment(fragment_calendar fragmentCalendar) {
        this.fragmentCalendar = fragmentCalendar;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bottomNavigationView.setForeground(null);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        sharedPreferences = view.getContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE);

        dataBaseHelper = new DataBaseHelper(view.getContext());

        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");

        if(!username.equals("") && !password.equals(""))
        {
            BKEL_USER user = new BKEL_USER(-1, username, password);
            user = dataBaseHelper.getOne(user);

            if(user != null)
            {
                bkTimeTableFragment = new BkTimeTableFragment(user.getId(), fragmentCalendar);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, bkTimeTableFragment).commit();
            }
        }

        username_txt = view.findViewById(R.id.username_txt);
        password_txt = view.findViewById(R.id.password_txt);
        username_layout = view.findViewById(R.id.username_layout);
        password_layout = view.findViewById(R.id.password_layout);
        btn_login = view.findViewById(R.id.btn_login);
        btn_clear = view.findViewById(R.id.btn_clear);

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username_txt.setText("");
                password_txt.setText("");
                username_layout.setError(null);
                password_layout.setError(null);
            }
        });

        username_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(username_layout.getError() != null)
                {
                    username_layout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(""))
                {
                    username_layout.setError("Username is needed");
                }
            }
        });

        password_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(password_layout.getError() != null)
                {
                    password_layout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(""))
                {
                    password_layout.setError("Password is needed");
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_txt.getText().toString();
                String password = password_txt.getText().toString();

                if(username.equals("") || password.equals(""))
                {
                    if(username.equals(""))
                        username_layout.setError("Tài khoản phải được nhập");
                    if(password.equals(""))
                        password_layout.setError("Mật khẩu phải được nhập");
                }
                else
                {
                    String encrypted_password = md5(password);
                    BKEL_USER bkel_user = new BKEL_USER(-1, username, encrypted_password);

//                    boolean success = dataBaseHelper.addOne(bkel_user);
//                    Toast.makeText(view.getContext(), "Success "+success, Toast.LENGTH_SHORT).show();
                    BKEL_USER user = dataBaseHelper.getOne(bkel_user);
                    if(user == null)
                    {
                        username_layout.setError("Tài khoản hoặc mật khẩu sai");
                        password_layout.setError("Tài khoản hoặc mật khẩu sai");
                    }
                    else
                    {
                        SharedPreferences.Editor editor = view.getContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE).edit();
                        editor.putString("username", user.getUsername());
                        editor.putString("password", user.getPassword());

                        editor.commit();

                        bkTimeTableFragment = new BkTimeTableFragment(user.getId(), fragmentCalendar);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, bkTimeTableFragment).commit();
                    }
                }

            }
        });
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        username_layout.setError(null);
        password_layout.setError(null);
    }

    public String md5(String password)
    {
        String encrypted_password;

        byte[] md5Input = password.getBytes();

        BigInteger md5Data = null;

        try {
            md5Data = new BigInteger(1, MD5.encryptMD5(md5Input));
        } catch (Exception e) {
            e.printStackTrace();
        }

        encrypted_password = md5Data.toString(16);

        if(encrypted_password.length() < 32)
        {
            encrypted_password = 0 + encrypted_password;
        }

        return encrypted_password;
    }
}