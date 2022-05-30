package Model;


public class todo_item {
    public todo_item(assignment assignments, meeting meetings){
       this.assignments = assignments;
       this.meetings = meetings;
    }

    private meeting meetings;
    private assignment assignments;


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
