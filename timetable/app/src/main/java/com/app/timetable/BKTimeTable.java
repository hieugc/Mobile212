package com.app.timetable;

public class BKTimeTable {
    private String name;
    private String location;
    private String time;
    private String week;

    public BKTimeTable(String name, String location, String time, String week) {
        this.name = name;
        this.location = location;
        this.time = time;
        this.week = week;
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

    @Override
    public String toString() {
        return "BKTimeTable{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                ", week='" + week + '\'' +
                '}';
    }
}
