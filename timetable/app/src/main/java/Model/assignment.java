package Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class assignment {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public assignment(int id, String title, String timeStart, String timeEnd, Boolean done){
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.title = title;
        this.done = done;
        this.id = id;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int year = getYear(timeEnd) - getYear(now);
        if (year < 0){
            this.time = "Đã hết hạn";
        }
        else{
            int nday_1 = numOfday(timeEnd);
            int nday_2 = numOfday(now);
            if(nday_1 < nday_2)
                this.time = "Đã hết hạn";
            else
                this.time = String.valueOf(nday_1 - nday_2) + " ngày";
        }
    }
    private ArrayList<list_check> list_checks;

    public ArrayList<list_check> getList_checks() {
        return list_checks;
    }

    public void setList_checks(ArrayList<list_check> list_checks) {
        this.list_checks = list_checks;
    }

    private int numOfday(String date){
        int y = getYear(date);
        int m = getMonth(date) - 1;
        int d = getDay(date);
        int res = d;
        while (m > 0){
            res += dayOfmonth(m);
            m-= 1;
        }
        if (y == 2023) res += 365;
        return res;
    }
    private int dayOfmonth(int m){
        switch (m){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2: return 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return 0;
    }
    private int getYear(String date){
        final int i = Integer.parseInt(date.split("/")[2]);
        return i;
    }
    private int getMonth(String date){
        final int i = Integer.parseInt(date.split("/")[1]);
        return i;
    }
    private int getDay(String date){
        final int i = Integer.parseInt(date.split("/")[0]);
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
