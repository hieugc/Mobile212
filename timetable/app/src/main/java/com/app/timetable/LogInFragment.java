package com.app.timetable;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LogInFragment extends Fragment {


    private TextInputEditText username_txt, password_txt;
    private TextInputLayout username_layout, password_layout;
    private Button btn_login, btn_clear;
    private BkTimeTableFragment bkTimeTableFragment;
    private DataBaseHelper dataBaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        dataBaseHelper = new DataBaseHelper(view.getContext());

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
                    password_layout.setError("Username is needed");
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
                    BKEL_USER bkel_user = new BKEL_USER(-1, username, password);
                    BKEL_USER user = dataBaseHelper.getOne(bkel_user);
                    if(user == null)
                    {
                        username_layout.setError("Tài khoản hoặc mật khẩu sai");
                        password_layout.setError("Tài khoản hoặc mật khẩu sai");
                    }
                    else
                    {
                        Toast.makeText(view.getContext(), "Success", Toast.LENGTH_SHORT).show();
                        bkTimeTableFragment = new BkTimeTableFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, bkTimeTableFragment).commit();
                    }
                    //Toast.makeText(view.getContext(), "Username: "+username+"\nPassword: "+password, Toast.LENGTH_SHORT).show();

                }

            }
        });
        return  view;
    }
}