package Model;

import java.util.ArrayList;

public class todo_item {
    public todo_item(assignment assignments, meeting meetings){
       this.assignments = assignments;
       this.meetings = meetings;
    }

    private meeting meetings;
    private assignment assignments;

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

    public int getType(){
        if (this.meetings == null) return 2;
        return 1;
    }
}
