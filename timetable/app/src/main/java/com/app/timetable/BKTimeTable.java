package com.app.timetable;

public class BKTimeTable {
    private int id;
    private String name;
    private String location;
    private String date;
    private String time;
    private String week;
    private String semester;
    private int userID;

    public BKTimeTable(int id, String name, String location, String date, String time, String week, String semester, int userID) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.week = week;
        this.semester = semester;
        this.userID = userID;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getUserID() {
        return userID;
    }

    public String getSemester() {
        return semester;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BKTimeTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", week='" + week + '\'' +
                ", semester='" + semester + '\'' +
                ", userID=" + userID +
                '}';
    }
}
