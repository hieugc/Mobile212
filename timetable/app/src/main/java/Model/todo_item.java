package Model;

import java.util.ArrayList;

public class todo_item {
    public todo_item(assignment assignments, ArrayList<list_check> list_checks, meeting meetings){
       this.assignments = assignments;
       this.list_checks = list_checks;
       this.meetings = meetings;
    }

    private meeting meetings;
    private assignment assignments;
    private ArrayList<list_check> list_checks;

    public void setAssignments(assignment assignments) {
        this.assignments = assignments;
    }

    public void setMeetings(meeting meetings) {
        this.meetings = meetings;
    }

    public assignment getAssignments() {
        return assignments;
    }

    public meeting getMeetings() {
        return meetings;
    }

    public void setList_checks(ArrayList<list_check> list_checks) {
        this.list_checks = list_checks;
    }

    public ArrayList<list_check> getList_checks() {
        return list_checks;
    }

    public int getType(){
        if (this.meetings == null) return 2;
        return 1;
    }
}
