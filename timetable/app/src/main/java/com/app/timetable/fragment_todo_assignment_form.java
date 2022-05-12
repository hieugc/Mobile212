package com.app.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class fragment_todo_assignment_form extends Fragment {

    public fragment_todo_assignment_form(){
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain_todo_form, this).commit();
    }

    ConstraintLayout todo_assignment_form_dialog, todo_assignment_form_dialog_add_time, todo_assignment_form_dialog_add_list;
    Button assignment_form_button_done;
    ImageView todo_assignment_form_close, todo_assignment_form_add_time, todo_assignment_form_add_list;
    TextInputEditText assignment_form_sub;
    fragment_todo todoView;

    public void setTodoView(fragment_todo view){
        todoView = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View assignment_form = inflater.inflate(R.layout.fragment_todo_meeting_form, container, false);

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

        //button
        assignment_form_button_done = assignment_form.findViewById(R.id.assignment_form_button_done);

        return assignment_form;
    }

    private void close(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, todoView).commit();
    }

}
