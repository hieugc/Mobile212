package Model;

public class assignment {
    public assignment(int id, String title, String time, Boolean done){
        this.time = time;
        this.title = title;
        this.done = done;
        this.id = id;
    }
    private String time;
    private String title;
    private Boolean done;
    private int id;

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
