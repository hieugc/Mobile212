package com.app.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Subject {
    private String className,classRoom,classGroup, note;
    private String startDate,endDate,startHour,endHour;
    private boolean[] studyDay = {false,false,false,false,false,false,false};
    private String lecturerName, lecturerMail, lecturerNumber;


    public Subject(String className, String classRoom,String classGroup, String note, String startdate,String enddate,
            String starthour,String endhour, boolean[] studyday){
        this.className = className;
        this.classRoom = classRoom;
        this.classGroup = classGroup;
        this.startDate = startdate;
        this.endDate = enddate;
        this.startHour = starthour;
        this.endHour = endhour;
        this.studyDay = studyday;
        this.note= note;
    }
    public Subject(String classname,String startHour,String endHour,String note){
        this.className = classname;
        this.startHour = startHour;
        this.endHour = endHour;
        this.note = note;
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
    public void setNote(String note){
        this.note = note;
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
    public String getNote(){
        return this.note;
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
    public int[] getStudyDay(){
        List<Integer> list = new ArrayList<Integer>();
        for (int index = 0; index < this.studyDay.length;index++){
            if (studyDay[index] == true) {
                list.add(index);
            }
        }
        int[] ret = new int[list.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = list.get(i).intValue();
        }
        return ret;
    }

}