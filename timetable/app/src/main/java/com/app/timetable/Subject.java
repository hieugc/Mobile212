package com.app.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Subject {
    private String className,classRoom,classGroup;
    private Date startDate,endDate,startHour,endHour;
    private boolean[] studyDay = {false,false,false,false,false,false,false};
    private String lecturerName, lecturerMail, lecturerNumber;

    public Subject(String className, String classRoom,String classGroup, Date startdate,Date enddate,
            Date starthour,Date endhour, boolean[] studyday){
        this.className = className;
        this.classRoom = classRoom;
        this.classGroup = classGroup;
        this.startDate = startdate;
        this.endDate = enddate;
        this.startHour = starthour;
        this.endHour = endhour;
        this.studyDay = studyday;
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
    public Date getStartDate(){
        return this.startDate;
    }
    public Date getEndDate(){
        return this.endDate;
    }
    public Date getStartHour(){
        return this.startHour;
    }
    public Date getEndHour(){
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
