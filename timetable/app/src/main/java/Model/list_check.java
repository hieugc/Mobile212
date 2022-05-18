package Model;

public class list_check {
    public list_check(int id, String content, Boolean done, int assign){
        this.content = content;
        this.link = -1;
        this.done = done;
        this.id = id;
        this.assign = assign;
    }
    private String content;
    private int link;
    private Boolean done;
    private int id;
    private int assign;

    public void setAssign(int assign) {
        this.assign = assign;
    }

    public int getAssign() {
        return assign;
    }

    public int getId() {
        return id;
    }

    public String getContent() { return content; }
    public int getLink() {
        return link;
    }
    public Boolean getDone() {
        return done;
    }
    public void setContent(String content) { this.content = content; }
    public void setLink(int link) {
        this.link = link;
    }
    public void setDone(Boolean done) {
        this.done = done;
    }

    public void setId(int id) {
        this.id = id;
    }
}
