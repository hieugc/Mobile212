package Model;

import android.view.View;

import com.app.timetable.fragment_todo_meeting_form;

public interface ItemClickListener {
    void onCheckClick(int pos, meeting meets);
    void onEditClick(meeting meets);
    void addListCheck(list_check listCheck);
    void addListCheckItem();
}
