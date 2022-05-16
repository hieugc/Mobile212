package com.app.timetable;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class fragment_todo_assignment_form extends Fragment {

    public fragment_todo_assignment_form(){
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain_todo_form, this).commit();
    }
    ConstraintLayout todo_assignment_form_dialog, todo_assignment_form_dialog_add_time, todo_assignment_form_dialog_add_list;
    Button assignment_form_button_done;
    ImageView todo_assignment_form_close, todo_assignment_form_add_time, todo_assignment_form_add_list;
    EditText assignment_form_sub;
    fragment_todo todoView;
    LinearLayout todo_assignment_time_show;

    //time show
    TextView todo_assignment_form_add_time_start, todo_assignment_form_add_time_end, todo_assignment_form_time_left;

    private static final String TAG = "MyActivity";
    public void setTodoView(fragment_todo view){
        todoView = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View assignment_form = inflater.inflate(R.layout.fragment_todo_assignment_form, container, false);

        todo_assignment_form_time_left = assignment_form.findViewById(R.id.todo_assignment_form_time_left);
        todo_assignment_form_add_time_start = assignment_form.findViewById(R.id.todo_assignment_form_add_time_start);
        todo_assignment_form_add_time_end = assignment_form.findViewById(R.id.todo_assignment_form_add_time_end);
        todo_assignment_time_show = assignment_form.findViewById(R.id.todo_assignment_time_show);


        //close
        todo_assignment_form_close = assignment_form.findViewById(R.id.todo_assignment_form_close);
                todo_assignment_form_close.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       close();
                    }
                });


        //title
        assignment_form_sub = assignment_form.findViewById(R.id.assignment_form_sub);

        //icon click
        todo_assignment_form_add_time = assignment_form.findViewById(R.id.todo_assignment_form_add_time);
        todo_assignment_form_add_list = assignment_form.findViewById(R.id.todo_assignment_form_add_list);

        //dialog
        todo_assignment_form_dialog = assignment_form.findViewById(R.id.todo_assignment_form_dialog);
        todo_assignment_form_dialog_add_time = assignment_form.findViewById(R.id.todo_assignment_form_dialog_add_time);
        todo_assignment_form_dialog_add_list = assignment_form.findViewById(R.id.todo_assignment_form_dialog_add_list);



        //onclick time



        //onclicklist



        //offdialog
        todo_assignment_form_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_dialog();
            }
        });
        todo_assignment_form_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_assignment_form_dialog.getVisibility() == View.INVISIBLE){
                    open_add_time_dialog();
                }
            }
        });
        todo_assignment_form_add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_assignment_form_dialog.getVisibility() == View.INVISIBLE){
                    open_add_list_dialog();
                }
            }
        });


        //button
        assignment_form_button_done = assignment_form.findViewById(R.id.assignment_form_button_done);
            assignment_form_button_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //tạo assignment

                    //close();
                }
        });


        return assignment_form;
    }

    private void close(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
    }

    private void time_show(CharSequence time_start, CharSequence time_end, CharSequence time_left){
        todo_assignment_time_show.setVisibility(View.VISIBLE);

        todo_assignment_form_add_time_start.setText(time_start);
        todo_assignment_form_add_time_start.setVisibility(View.VISIBLE);

        todo_assignment_form_add_time_end.setText(time_end);
        todo_assignment_form_add_time_end.setVisibility(View.VISIBLE);

        todo_assignment_form_time_left.setText(time_left);
        todo_assignment_form_time_left.setVisibility(View.VISIBLE);
    }

    private void close_dialog(){
        close_add_list_dialog();
        close_add_time_dialog();
    }
    private void close_add_time_dialog(){
        todo_assignment_form_dialog.setVisibility(View.INVISIBLE);
        todo_assignment_form_dialog_add_time.setVisibility(View.INVISIBLE);
    }
    private void close_add_list_dialog(){
        todo_assignment_form_dialog.setVisibility(View.INVISIBLE);
        todo_assignment_form_dialog_add_list.setVisibility(View.INVISIBLE);
    }
    private void open_add_time_dialog(){
        todo_assignment_form_dialog.setVisibility(View.VISIBLE);
        todo_assignment_form_dialog_add_time.setVisibility(View.VISIBLE);
    }
    private void open_add_list_dialog(){
        todo_assignment_form_dialog.setVisibility(View.VISIBLE);
        todo_assignment_form_dialog_add_list.setVisibility(View.VISIBLE);
    }
}
