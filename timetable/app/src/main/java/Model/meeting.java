package Model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.timetable.R;

public class meeting{
    public meeting(int id, String time, String title, String location, String link, String alert, Boolean done){
        this.time = time;
        this.title = title;
        this.location = location;
        this.link = link;
        this.alert = alert;
        this.done = done;
        this.id = id;
    }
    private String time;
    private String title;
    private String location;
    private String link;
    private String alert;
    private Boolean done;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlert() {
        return alert;
    }

    public String getLink() {
        return link;
    }

    public String getLocation() {
        return location;
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

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setLocation(String location) {
        this.location = location;
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
