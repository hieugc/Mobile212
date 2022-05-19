package Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class assignment {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public assignment(int id, String title, String timeStart, String timeEnd, Boolean done){
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.title = title;
        this.done = done;
        this.id = id;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        int year = getYear(timeEnd) - getYear(now);
        if (year != 0){
            this.time = year + " năm";
        }
        else {
            int month = getMonth(timeEnd) - getMonth(now);
            if (month != 0){
                this.time = month + " tháng";
            }
            else {
                int day = getDay(timeEnd) - getDay(now);
                if (day != 0){
                    this.time = day + " ngày";
                }
                else {
                    int hour = getHour(timeEnd) - getHour(now);
                    int minus = getMinus(timeEnd) - getMinus(now);
                    if (hour < 10)
                        this.time = "0" + hour + ":";
                    else
                        this.time = hour + ":";
                    if (minus < 10)
                        this.time += "0" + minus;
                    else
                        this.time += minus;
                }
            }
        }
    }

    private int getYear(String date){
        final int i = Integer.parseInt(date.split(" ")[1].split("/")[2]);
        return i;
    }
    private int getMonth(String date){
        final int i = Integer.parseInt(date.split(" ")[1].split("/")[1]);
        return i;
    }
    private int getDay(String date){
        final int i = Integer.parseInt(date.split(" ")[1].split("/")[0]);
        return i;
    }
    private int getHour(String date){
        final int i = Integer.parseInt(date.split(" ")[0].split(":")[0]);
        return i;
    }
    private int getMinus(String date){
        final int i = Integer.parseInt(date.split(" ")[0].split(":")[1]);
        return i;
    }
    private String timeStart, timeEnd;
    private String title;
    private Boolean done;
    private int id;
    private String time;

    public int getId() {
        return id;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
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
