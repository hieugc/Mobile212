package Model;

import java.util.ArrayList;

public class todo_item {
    public todo_item(ArrayList<assignment> assignments, ArrayList<list_check> list_checks, ArrayList<meeting> meetings){
       this.assignments = assignments;
       this.list_checks = list_checks;
       this.meetings = meetings;
    }

    private ArrayList<meeting> meetings;
    private ArrayList<assignment> assignments;
    private ArrayList<list_check> list_checks;

    public void setMeetings(ArrayList<meeting> meetings) {
        this.meetings = meetings;
    }

    public void setList_checks(ArrayList<list_check> list_checks) {
        this.list_checks = list_checks;
    }

    public void setAssignments(ArrayList<assignment> assignments) {
        this.assignments = assignments;
    }

    public ArrayList<list_check> getList_checks() {
        return list_checks;
    }

    public ArrayList<assignment> getAssignments() {
        return assignments;
    }

    public ArrayList<meeting> getMeetings() {
        return meetings;
    }

    public int getType(){
        if (this.meetings == null) return 0;
        return 1;
    }
}
