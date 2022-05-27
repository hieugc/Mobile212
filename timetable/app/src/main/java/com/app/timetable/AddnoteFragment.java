package com.app.timetable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddnoteFragment extends Fragment {
    private ImageButton back_btn;
    private fragment_note fragmentNote;
    private EditText title_txt, content_txt;
    private TextView done_txt;
    private DataBaseHelper dataBaseHelper;

    private TextView add_note_txt;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addnote, container, false);

        add_note_txt = view.findViewById(R.id.add_note_txt);

        dataBaseHelper = new DataBaseHelper(view.getContext());

        back_btn = view.findViewById(R.id.back_btn_note);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fragmentNote = new fragment_note();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragmentNote).commit();
            }
        });


        title_txt = view.findViewById(R.id.note_heading_editText);
        content_txt = view.findViewById(R.id.note_content_editText);
        done_txt = view.findViewById(R.id.done_txt);

        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = title_txt.getText().toString();
                String content = content_txt.getText().toString();

                Note note = new Note(-1, title, content, "");

                boolean success = dataBaseHelper.addOne(note);
                Toast.makeText(view.getContext(), "Success "+ success, Toast.LENGTH_SHORT).show();
                fragmentNote = new fragment_note();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,fragmentNote).commit();

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();
        if(bundle != null){
            String func = bundle.getString("func");
            if (func.trim().equals("openInfoNote")){
                Note note = bundle.getParcelable("note");
                title_txt.setText(note.getTitle());
                content_txt.setText(note.getContent());
                add_note_txt.setText("Tất cả Ghi chú");
                done_txt.setText("Xong");
                done_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (title_txt.getText().toString().trim().equals("")){
                            dataBaseHelper.deleteOne(note);
                        }
                        else {
                            dataBaseHelper.updateLinkedNote(note);
                        }
                        fragmentNote = new fragment_note();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,fragmentNote).commit();
                    }
                });
                add_note_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragmentNote = new fragment_note();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,fragmentNote).commit();
                    }
                });
            }
            else{
                title_txt.setText(bundle.getString("title"));
                add_note_txt.setText(bundle.getString("head_back"));
                done_txt.setText(bundle.getString("head_add"));
                if (func == "link_note"){
                    done_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String title = title_txt.getText().toString();
                            Log.e("no", "|" + title.trim() + "|");
                            if (title.trim().equals("")){
                                returnNote(bundle, null, "linked_note");
                            }
                            else{
                                String content = content_txt.getText().toString();
                                returnNote(bundle, new Note(-1, title, content, ""), "linked_note");
                            }
                        }
                    });
                    back_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            returnNote(bundle, null, "linked_note");
                        }
                    });
                    add_note_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            returnNote(bundle, null, "linked_note");
                        }
                    });
                }
                else if (func == "open_note"){
                    content_txt.setText(bundle.getString("content"));
                    Note note = bundle.getParcelable("note");
                    done_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String title = title_txt.getText().toString();
                            if (title.trim().equals("")){
                                returnNote(bundle, null, "opened_note" + bundle.getString("type"));
                            }
                            else{
                                String content = content_txt.getText().toString();
                                note.setContent(content);
                                note.setTitle(title);
                                returnNote(bundle, note, "opened_note" + bundle.getString("type"));
                            }
                        }
                    });
                    back_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            returnNote(bundle, note, "opened_note" + bundle.getString("type"));
                        }
                    });
                    add_note_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            returnNote(bundle, note, "opened_note" + bundle.getString("type"));
                        }
                    });
                }
                else if (func.trim().equals("todoOpen")){
                    content_txt.setText(bundle.getString("content"));
                    Note note = bundle.getParcelable("note");
                    done_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String title = title_txt.getText().toString();
                            if (title.trim().equals("")){
                                returnTodo(bundle, null);
                            }
                            else{
                                String content = content_txt.getText().toString();
                                note.setContent(content);
                                note.setTitle(title);
                                returnTodo(bundle, note);
                            }
                        }
                    });
                    back_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            returnTodo(bundle, note);
                        }
                    });
                    add_note_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            returnTodo(bundle, note);
                        }
                    });
                }
            }
            this.setArguments(null);
        }
    }
    private void returnTodo(Bundle bundle, Note note){
        Bundle bundle1 = new Bundle();
        bundle1.putString("func", "todoOpen");
        bundle1.putParcelable("note", note);
        bundle1.putParcelable("listCheck", bundle.getParcelable("listCheck"));

        fragment_todo fragmentTodo = bundle.getParcelable("todoView");
        fragmentTodo.setArguments(bundle1);

        getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragmentTodo).commit();
    }

    private void returnNote(Bundle bundle, Note note, String func){
        Bundle bundle1 = new Bundle();
        bundle1.putString("func", func);

        bundle1.putParcelableArrayList("list_checks", bundle.getParcelableArrayList("list_checks"));
        bundle1.putParcelableArrayList("list_checks_dialog", bundle.getParcelableArrayList("list_checks_dialog"));
        bundle1.putParcelableArrayList("list_note", bundle.getParcelableArrayList("list_note"));
        bundle1.putString("sub", bundle.getString("sub"));
        bundle1.putString("time_start", bundle.getString("time_start"));
        bundle1.putString("time_end", bundle.getString("time_end"));
        bundle1.putString("time_left", bundle.getString("time_left"));
        bundle1.putParcelable("todoView", bundle.getParcelable("todoView"));

        bundle1.putParcelable("note", note);
        bundle1.putParcelable("listCheck", bundle.getParcelable("listCheck"));
        bundle1.putString("bundle", bundle.getString("bundle"));
        bundle1.putInt("_id_", bundle.getInt("_id_"));
        fragment_todo_assignment_form fragment_todo_assignment_form = new fragment_todo_assignment_form();
        fragment_todo_assignment_form.setArguments(bundle1);

        getParentFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragment_todo_assignment_form).commit();
    }
}