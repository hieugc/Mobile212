package com.app.timetable;

import io.realm.RealmObject;

public class Note extends RealmObject {
     private int id;
     private String title;
     private String content;
//     private long createdTime;
    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public Note(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
