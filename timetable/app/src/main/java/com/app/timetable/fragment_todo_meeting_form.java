package com.app.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class fragment_todo_meeting_form extends Fragment {

    public fragment_todo_meeting_form(){
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain_todo_form, this).commit();
    }
    TextInputEditText subtitle, location, link;
    ImageView meet_form_close, todo_meet_form_add_time;
    Button meet_form_button_done;
    fragment_todo fragment_todo;
    ConstraintLayout todo_meet_form_add_time_dialog;

    public void setTodoView(fragment_todo fragment) {
        fragment_todo = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View meeting_form = inflater.inflate(R.layout.fragment_todo_meeting_form, container, false);

        meet_form_close = meeting_form.findViewById(R.id.todo_meet_form_close); //done
        meet_form_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_form();
            }
        });

        subtitle = meeting_form.findViewById(R.id.meeting_form_sub);
        location = meeting_form.findViewById(R.id.meeting_form_local);
        link = meeting_form.findViewById(R.id.meeting_form_link);
        todo_meet_form_add_time = meeting_form.findViewById(R.id.todo_meet_form_add_time);
        todo_meet_form_add_time_dialog = meeting_form.findViewById(R.id.todo_meet_form_add_time_dialog);

        todo_meet_form_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo_meet_form_add_time_dialog.getVisibility() == View.INVISIBLE){
                    todo_meet_form_add_time_dialog.setVisibility(View.VISIBLE);
                }
                else{
                    todo_meet_form_add_time_dialog.setVisibility(View.INVISIBLE);
                }
            }
        });

        todo_meet_form_add_time_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(todo_meet_form_add_time_dialog.getVisibility() == View.VISIBLE){
                    todo_meet_form_add_time_dialog.setVisibility(View.INVISIBLE);
                }
            }
        });
        meet_form_button_done = meeting_form.findViewById(R.id.meet_form_button_done);

        return meeting_form;
    }

    private void close_form() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragment_todo).commit();
    }
}
