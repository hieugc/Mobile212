package Model;

public class list_check {
    public list_check(String content, String link, Boolean done){
        this.content = content;
        this.link = link;
        this.done = done;
    }
    private String content;
    private String link;
    private Boolean done;
    public String getContent() { return content; }
    public String getLink() {
        return link;
    }
    public Boolean getDone() {
        return done;
    }
    public void setContent(String content) { this.content = content; }
    public void setLink(String link) {
        this.link = link;
    }
    public void setDone(Boolean done) {
        this.done = done;
    }
}
