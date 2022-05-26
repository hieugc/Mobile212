package Model;

import android.view.View;

import com.app.timetable.fragment_todo_meeting_form;

public interface ItemClickListener {
    void onCheckClick(meeting meets);
    void onEditClick(meeting meets);
    void addListCheck(list_check listCheck);
    void addListCheckItem();
    void removeListCheckItem(list_check listCheck);
    void removeListCheck(list_check listCheck);
    void openAddListCheck();
    void editAssignment(assignment assign);
    void onCheckAssign(assignment assign);
    int linkNewNote(list_check listCheck);
    void unlinkNote(int id);
    void openNote(list_check listCheck, String type);
}
