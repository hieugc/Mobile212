package com.app.timetable;

public class TimeTable {
    private int id;
    private String name;
    private String group;
    private String location;
    private String date;
    private String start_time;
    private String end_time;
    private String TA_name;
    private String TA_number;
    private String TA_email;
    private boolean notification;
    private String notification_time;
    private int type;
    private int timetable_id;

    public TimeTable(int id, String name, String group, String location, String date, String start_time, String end_time, String TA_name, String TA_number, String TA_email, boolean notification, String notification_time, int type, int timetable_id) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.location = location;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.TA_name = TA_name;
        this.TA_number = TA_number;
        this.TA_email = TA_email;
        this.notification = notification;
        this.notification_time = notification_time;
        this.type = type;               // 1: for Subject, 2 for BKTimeTable
        this.timetable_id = timetable_id;   //
    }

    public TimeTable(int id, String name, String group, String location, String date, String start_time, String end_time, int type, int timetable_id) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.location = location;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.TA_name = "";
        this.TA_number = "";
        this.TA_email = "";
        this.notification = true;
        this.notification_time = "0:05";
        this.type = type;
        this.timetable_id = timetable_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTA_name() {
        return TA_name;
    }

    public void setTA_name(String TA_name) {
        this.TA_name = TA_name;
    }

    public String getTA_number() {
        return TA_number;
    }

    public void setTA_number(String TA_number) {
        this.TA_number = TA_number;
    }

    public String getTA_email() {
        return TA_email;
    }

    public void setTA_email(String TA_email) {
        this.TA_email = TA_email;
    }

    public boolean getNotification() {
        return notification;
    }


    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTimetable_id() {
        return timetable_id;
    }

    public void setTimetable_id(int timetable_id) {
        this.timetable_id = timetable_id;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", TA_name='" + TA_name + '\'' +
                ", TA_number='" + TA_number + '\'' +
                ", TA_email='" + TA_email + '\'' +
                ", notification=" + notification +
                ", notification_time='" + notification_time + '\'' +
                ", type=" + type +
                ", timetable_id=" + timetable_id +
                '}';
    }
}
