package com.app.timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subject {
    private String className,classRoom,classGroup;
    private String startDate,endDate,startHour,endHour;
    private boolean[] studyDay;
    private String study;
    private String lecturerName, lecturerMail, lecturerNumber;


    public Subject(String className, String classRoom,String classGroup, String startdate,String enddate,
            String starthour,String endhour, boolean[] studyday,String lecturerName,String lecturerNumber,String lecturerMail){
        this.className = className;
        this.classRoom = classRoom;
        this.classGroup = classGroup;
        this.startDate = startdate;
        this.endDate = enddate;
        this.startHour = starthour;
        this.endHour = endhour;
        this.studyDay = studyday;
        this.lecturerName = lecturerName;
        this.lecturerNumber = lecturerNumber;
        this.lecturerMail = lecturerMail;
    }

    public void setClassName(String name){
        this.className = name;
    }
    public void setClassGroup(String classGroup){
        this.classGroup = classGroup;
    }
    public void setStartDate(String date){
        this.startDate = date;
    }
    public void setClassRoom(String room){
        this.classRoom = room;
    }
    public void setEndDate(String date){
        this.endDate = date;
    }
    public void setStartHour(String hour){
        this.startHour = hour;
    }
    public void setEndHour(String hour){
        this.endHour = hour;
    }
    public void setLecturerName(String name){
        this.lecturerName = name;
    }
    public void setLecturerMail(String mail){
        this.lecturerMail = mail;
    }
    public void setLecturerNumber(String number){
        this.lecturerNumber = number;
    }
    public String getLecturerName(){
        return lecturerName;
    }
    public String getLecturerMail(){
        return lecturerMail;
    }
    public String getLecturerNumber(){
        return lecturerNumber;
    }
    public String getClassName(){
        return this.className;
    }
    public String getClassGroup(){
        return this.classGroup;
    }
    public String getClassRoom(){
        return this.classRoom;
    }
    public String getStartDate(){
        return this.startDate;
    }
    public String getEndDate(){
        return this.endDate;
    }
    public String getStartHour(){
        return this.startHour;
    }
    public String getEndHour(){
        return this.endHour;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public boolean[] getStudyDate()
    {
        return studyDay;
    }

    public void setStudyDay(boolean[] studyDay) {
        this.studyDay = studyDay;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "className='" + className + '\'' +
                ", classRoom='" + classRoom + '\'' +
                ", classGroup='" + classGroup + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startHour='" + startHour + '\'' +
                ", endHour='" + endHour + '\'' +
                ", studyDay=" + Arrays.toString(studyDay) +
                ", lecturerName='" + lecturerName + '\'' +
                ", lecturerMail='" + lecturerMail + '\'' +
                ", lecturerNumber='" + lecturerNumber + '\'' +
                '}';
    }
}
