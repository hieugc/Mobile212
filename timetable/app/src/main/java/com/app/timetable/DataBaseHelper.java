package com.app.timetable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import Model.assignment;
import Model.list_check;
import Model.meeting;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String TABLE_BKEL = "BKEL_ACC";
    public static final String COLUMN_USER = "USER";
    public static final String COLUMN_PWD = "PWD";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_CONTENT = "CONTENT";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_TIME_START = "TIME_START";
    public static final String COLUMN_TIME_END = "TIME_END";
    public static final String COLUMN_LOCATION = "LOCATION";
    public static final String COLUMN_LINK = "LINK";
    public static final String COLUMN_ALERT = "ALERT";
    public static final String TABLE_NOTE = "NOTE";
    public static final String COLUMN_LINK_NOTE = "LINK_" + TABLE_NOTE;
    public static final String TABLE_MEET = "MEET";
    public static final String TABLE_ASSIGN = "ASSIGN";
    public static final String TABLE_CHECKLIST = "CHECKLIST";
    public static final String COLUMN_CHECKED = "CHECKED";
    public static final String TABLE_BK_TIMETABLE = "BK_TIMETABLE";
    public static final String COLUMN_USERID = "USERID";
    public static final String COLUMN_SEMESTER = "SEMESTER";
    public static final String COLUMN_WEEK = "WEEK";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_GROUP = "TEAM";
    public static final String STRING_SEMESTER = "\"212\"";
    public static final String SEMESTER = "212";
    public static final String COLUMN_LINK_ASSIGN = "LINK_" + TABLE_ASSIGN;
    public static final String COLUMN_CREATED_AT = "CREATED_AT";
    public static final String COLUMN_START_TIME = "START_TIME";
    public static final String COLUMN_END_TIME = "END_TIME";
    public static final String COLUMN_TA_NAME = "TA_NAME";
    public static final String COLUMN_TA_NUMBER = "TA_NUMBER";
    public static final String COLUMN_TA_EMAIL = "TA_EMAIL";
    public static final String COLUMN_TIMETABLE_ID = "TIMETABLE_ID";
    public static final String COLUMN_TIMETABLE_TYPE = "TIMETABLE_TYPE";
    public static final String COLUMN_NOTIFICATION = "NOTI";
    public static final String COLUMN_NOTIFICATION_TIME = "NOTI_TIME";
    public static final String TABLE_TIMETABLE = "TIMETABLE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "Mobile.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_BKEL
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER + " STRING UNIQUE,"
                + COLUMN_PWD + " STRING" +
                ")";
        db.execSQL(sql);


        sql = "CREATE TABLE " + TABLE_BK_TIMETABLE
                + " ( " +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " STRING, " +
                COLUMN_GROUP + " TEXT , " +
                COLUMN_LOCATION+" STRING, " +
                COLUMN_DATE+" STRING, "+
                COLUMN_TIME+" STRING, " +
                COLUMN_WEEK + " TEXT, " +
                COLUMN_SEMESTER + " STRING, " +
                COLUMN_USERID + " INT, FOREIGN KEY("+COLUMN_USERID+") REFERENCES "+TABLE_BKEL+"("+COLUMN_ID+") )";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_TIMETABLE + " (" +
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME+ " STRING," +
                COLUMN_GROUP+" STRING," +
                COLUMN_LOCATION+" STRING," +
                COLUMN_DATE+" TEXT, " +
                COLUMN_START_TIME + " TEXT," +
                COLUMN_END_TIME + " TEXT," +
                COLUMN_TA_NAME + " STRING," +
                COLUMN_TA_NUMBER + " INTEGER," +
                COLUMN_TA_EMAIL + " TEXT," +
                COLUMN_NOTIFICATION + " INTEGER," +
                COLUMN_NOTIFICATION_TIME + " INTEGER," +
                COLUMN_TIMETABLE_TYPE + " INTEGER," +
                COLUMN_TIMETABLE_ID + " INTEGER)";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_NOTE
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " STRING, "
                + COLUMN_CONTENT + " TEXT," +
                COLUMN_CREATED_AT + " TEXT)";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_MEET
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " STRING, "
                + COLUMN_TIME + " STRING, "
                + COLUMN_LOCATION + " STRING, "
                + COLUMN_LINK + " STRING, "
                + COLUMN_ALERT + " STRING,"
                + COLUMN_CHECKED + " BOOL )";
        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_ASSIGN
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " STRING, "
                + COLUMN_TIME_START + " STRING,"
                + COLUMN_TIME_END + " STRING,"
                + COLUMN_CHECKED + " BOOL)";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_CHECKLIST
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CONTENT + " STRING, "
                + COLUMN_LINK_NOTE + " INTEGER,"
                + COLUMN_LINK_ASSIGN + " INTEGER,"
                + COLUMN_CHECKED + " BOOL, FOREIGN KEY("+COLUMN_LINK_NOTE+") REFERENCES "+TABLE_NOTE+"("+COLUMN_ID+") )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean execQuery(String sql)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        return true;
    }

    public boolean addOne(BKEL_USER user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "INSERT INTO "+TABLE_BKEL
                +" ("+COLUMN_USER+","+COLUMN_PWD+")" +
                " VALUES (\""+user.getUsername()+"\",\""+user.getPassword()+"\")";

        db.execSQL(sql);
        return true;
    }
    public BKEL_USER getOne(BKEL_USER user)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_BKEL+" WHERE "+COLUMN_USER+" = \""+user.getUsername()+"\" AND "+COLUMN_PWD+" = \""+user.getPassword()+"\"";

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            BKEL_USER bkel_user = new BKEL_USER(id, username, password);
            return bkel_user;
        }
        else
        {
            return null;
        }
    }

    public boolean addOne(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "INSERT INTO "+TABLE_NOTE+" " +
                "("+COLUMN_TITLE+","+COLUMN_CONTENT+ ", "+COLUMN_CREATED_AT+")" +
                "VALUES(\""+note.getTitle()+"\",\""+note.getContent()+"\"," +
                "datetime(\"now\",\"localtime\"))";

        db.execSQL(sql);


        return true;
    }

    public boolean addOne(TimeTable timeTable)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql ="INSERT INTO "+TABLE_TIMETABLE+" (" +
                COLUMN_ID+","+COLUMN_NAME+","+COLUMN_GROUP+","+COLUMN_LOCATION+","+COLUMN_DATE+","+COLUMN_START_TIME+","+COLUMN_END_TIME+","+COLUMN_TA_NAME+","+COLUMN_TA_NUMBER+","+COLUMN_TA_EMAIL+","+COLUMN_NOTIFICATION+","+COLUMN_NOTIFICATION_TIME+","+COLUMN_TIMETABLE_TYPE+","+COLUMN_TIMETABLE_ID+" ) " +
                "VALUES (\""+timeTable.getId()+"\",\""+timeTable.getName()+"\",\""+timeTable.getGroup()+"\",\""+timeTable.getDate()+"\",\""+timeTable.getStart_time()+"\",\""+timeTable.getEnd_time()+"\",\""+timeTable.getTA_name()+"\",\""+timeTable.getTA_number()+"\",\""+timeTable.getTA_email()+"\","+timeTable.getNotification()+",\""+timeTable.getNotification_time()+"\","+timeTable.getType()+","+timeTable.getTimetable_id()+")";

        db.execSQL(sql);
        return true;
    }

    public ArrayList<Note> getAllNote()
    {
        ArrayList<Note> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NOTE;

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String date = cursor.getString(3);

                Note note = new Note(id, title, content, date);
                arrayList.add(note);
            }while(cursor.moveToNext());
        }
        else
        {
            //failure, do nothing
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public boolean deleteOne(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "DELETE FROM " + TABLE_NOTE + " WHERE "+ COLUMN_ID +" = "+note.getId();

        db.execSQL(sql);
        return true;
    }

    public boolean addOne(BKTimeTable bkTimeTable)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "INSERT INTO "+TABLE_BK_TIMETABLE+" ("
                +COLUMN_NAME+","+COLUMN_GROUP+","+COLUMN_LOCATION+","+COLUMN_DATE+","+COLUMN_TIME+","+COLUMN_WEEK+","+COLUMN_SEMESTER+","+COLUMN_USERID+") " +
                "VALUES(\""+bkTimeTable.getName()+"\",\""+bkTimeTable.getGroup()+"\",\""+bkTimeTable.getLocation()+"\",\""+bkTimeTable.getDate()+"\",\""+bkTimeTable.getTime()+"\",\""+bkTimeTable.getWeek()+"\",\""+bkTimeTable.getSemester()+"\","+bkTimeTable.getUserID()+")";

        db.execSQL(sql);
        return true;
    }

    public ArrayList<BKTimeTable> getTimeTable(int userID)
    {
        ArrayList<BKTimeTable> arrayList = new ArrayList<>();

        String sql = "SELECT * FROM "+TABLE_BK_TIMETABLE+" WHERE "
                +COLUMN_USERID+" = "+userID+" AND "
                +COLUMN_SEMESTER+ " = " + STRING_SEMESTER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String group = cursor.getString(2);
                String location = cursor.getString(3);
                String date = cursor.getString(4);
                String time =cursor.getString(5);
                String week = cursor.getString(6);

                BKTimeTable bkTimeTable = new BKTimeTable(id, name, group, location, date, time, week, SEMESTER, userID);

                arrayList.add(bkTimeTable);
            }while(cursor.moveToNext());
        }
        else
        {
            //failure. DO NOTHING
        }
        db.close();
        cursor.close();
        return arrayList;
    }

    public BKTimeTable getTimeTable(int userID, String timeTableName)
    {
        String sql = "SELECT * FROM "+TABLE_BK_TIMETABLE+" WHERE "
                +COLUMN_USERID+" = "+userID+" AND "
                +COLUMN_SEMESTER+ " = " + STRING_SEMESTER +" AND " +
                COLUMN_NAME + " = \"" + timeTableName +"\"";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String group = cursor.getString(2);
            String location = cursor.getString(3);
            String date = cursor.getString(4);
            String time =cursor.getString(5);
            String week = cursor.getString(6);

            BKTimeTable bkTimeTable = new BKTimeTable(id, name, group,  location, date, time, week, SEMESTER, userID);
            db.close();
            cursor.close();
            return bkTimeTable;
        }
        else
        {
            //failure. DO NOTHING
            db.close();
            cursor.close();
            return null;
        }
    }

    public int addOne(meeting meet)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql ="INSERT INTO "+TABLE_MEET
                +   " ("+ COLUMN_TITLE + ", " + COLUMN_TIME + ", " + COLUMN_LOCATION + ", " + COLUMN_LINK + ", " + COLUMN_ALERT + ", " + COLUMN_CHECKED + ")"
                +   " VALUES (\"" + meet.getTitle() +"\", \"" + meet.getTime() + "\", \"" + meet.getLocation() + "\", \"" + meet.getLink() + "\", \"" + meet.getAlert() + "\", " + meet.getDone() + ");";
        Log.e("DB", sql);
        db.execSQL(sql);

        return getID(TABLE_MEET);
    }
    private int getID(String TABLE){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql =   "SELECT " + COLUMN_ID + "\n" +
                "FROM " + TABLE + "\n" +
                "ORDER BY " + COLUMN_ID + " DESC\n" +
                "LIMIT 1;";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public ArrayList<meeting> getAllMeet()
    {
        ArrayList<meeting> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_MEET;

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do{
                int id_res = cursor.getInt(0);
                String title = cursor.getString(1);
                String time = cursor.getString(2);
                String location = cursor.getString(3);
                String link = cursor.getString(4);
                String alert = cursor.getString(5);
                boolean done = Boolean.parseBoolean(cursor.getString(6));

                arrayList.add(new meeting(id_res, time, title, location, link, alert, done));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    public boolean deleteOne(meeting meeting)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "DELETE FROM " + TABLE_MEET + " WHERE " + COLUMN_ID + " = " + meeting.getId();

        db.execSQL(sql);
        return true;
    }
    public boolean updateOne(meeting meeting){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql =  "UPDATE " + TABLE_MEET
                    + " SET " + COLUMN_TITLE + " = \"" + meeting.getTitle() + "\", "
                            + COLUMN_TIME + " = \"" + meeting.getTime() + "\", "
                            + COLUMN_LINK + " = \"" + meeting.getLink() + "\", "
                            + COLUMN_LOCATION + " = \"" + meeting.getLocation() + "\", "
                            + COLUMN_ALERT + " = \"" + meeting.getAlert() + "\", "
                            + COLUMN_CHECKED + " = " + meeting.getDone()
                    + " WHERE " + COLUMN_ID + " = " + meeting.getId() + ";";

        db.execSQL(sql);
        return true;
    }

    public int addOne(list_check listCheck)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "";
        if (listCheck.getLink() != -1){
            sql ="INSERT INTO "+TABLE_CHECKLIST
                    +   " (\""+ COLUMN_CONTENT + "\"," + COLUMN_LINK_NOTE + ", " + COLUMN_LINK_ASSIGN + ", " + COLUMN_CHECKED + ")"
                    +   " VALUES (\"" + listCheck.getContent() +"\", " + listCheck.getLink() + ", " + listCheck.getAssign() + ", " + listCheck.getDone() +  ")";
        }
        else{
            sql ="INSERT INTO "+TABLE_CHECKLIST
                    +   " (\""+ COLUMN_CONTENT + "\"," + COLUMN_LINK_NOTE + ", " + COLUMN_LINK_ASSIGN + ", " + COLUMN_CHECKED + ")"
                    +   " VALUES (\"" + listCheck.getContent() +"\", NULL, " + listCheck.getAssign() + ", " + listCheck.getDone() +  ")";

        }

        db.execSQL(sql);
        return getID(TABLE_CHECKLIST);
    }
    public int linkNote(Note note){
        if(addOne(note)) return getID(TABLE_NOTE);
        return -1;
    }
    public boolean updateLinkedNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE FROM " + TABLE_NOTE + "\n"
                    +"SET " + COLUMN_TITLE + " = \"" + note.getTitle() + "\"\n"
                    +      COLUMN_CONTENT + " = \"" + note.getContent() + "\"\n"
                    +"WHERE " + COLUMN_ID + " = " + note.getId();
        db.execSQL(sql);
        return true;
    }

    public ArrayList<list_check> getAllListCheck(int id)
    {
        ArrayList<list_check> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CHECKLIST
                    +" WHERE " + COLUMN_LINK_ASSIGN + " = " + id;

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do{
                int id_res = cursor.getInt(0);
                String content = cursor.getString(1);
                int link = cursor.getInt(2);
                int assign = cursor.getInt(3);
                boolean done = Boolean.parseBoolean(cursor.getString(4));
                list_check listCheck = new list_check(id_res, content, done, assign);
                listCheck.setLink(link);
                arrayList.add(listCheck);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    public boolean deleteOne(list_check listCheck)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "DELETE FROM " + TABLE_CHECKLIST + " WHERE " + COLUMN_ID + " = " + listCheck.getId();

        db.execSQL(sql);
        return true;
    }

    public boolean updateOne(list_check listCheck){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql =  "UPDATE FROM " + TABLE_CHECKLIST
                + " SET " + COLUMN_CONTENT + " = \"" + listCheck.getContent() + "\", "
                        + COLUMN_LINK_NOTE + " = " + listCheck.getLink() + ", "
                        + COLUMN_LINK_ASSIGN + " = " + listCheck.getAssign() + ", "
                        + COLUMN_CHECKED + " = " + listCheck.getDone()
                + " WHERE " + COLUMN_ID + " = " + listCheck.getId();

        db.execSQL(sql);
        return true;
    }


    public int addOne(assignment assignment)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql ="INSERT INTO "+TABLE_ASSIGN
                +   " ("+ COLUMN_TITLE + ", " + COLUMN_TIME_START + ", " + COLUMN_TIME_END + ", " + COLUMN_CHECKED + ")"
                +   " VALUES (\"" + assignment.getTitle() +"\", \"" + assignment.getTimeStart() + "\", \"" + assignment.getTimeEnd() + "\", " + assignment.getDone() +  ")";

        db.execSQL(sql);
        return getID(TABLE_ASSIGN);
    }

    public ArrayList<assignment> getAllAssignment()
    {
        ArrayList<assignment> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ASSIGN;

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do{
                int id_res = cursor.getInt(0);
                String content = cursor.getString(1);
                String time_s = cursor.getString(2);
                String time_e = cursor.getString(3);
                boolean done = Boolean.parseBoolean(cursor.getString(4));
                ArrayList<list_check> list_checks = getAllListCheck(id_res);
                assignment assignment = new assignment(id_res, content, time_s, time_e, done);
                assignment.setList_checks(list_checks);
                arrayList.add(assignment);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }
    public ArrayList<Note> getOne(ArrayList<list_check> list_checks){
        ArrayList<Note> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        for (list_check l: list_checks){
            if (l.getLink() != -1){
                String sql = "SELECT * FROM " + TABLE_NOTE + " WHERE " + COLUMN_ID + " = " + l.getLink();
                Cursor cursor = db.rawQuery(sql, null);
                if(cursor.moveToFirst())
                {
                    int id_res = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String content = cursor.getString(2);
                    String date = cursor.getString(3);
                    arrayList.add(new Note(id_res, title, content, date));
                }
                cursor.close();
            }
            else{
                arrayList.add(null);
            }
        }

        db.close();
        return arrayList;
    }
    public int unlinkNote(list_check listCheck){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE FROM " + TABLE_CHECKLIST + " SET " + COLUMN_LINK_NOTE + " = NULL " + " WHERE " + COLUMN_ID + " = " + listCheck.getId();
        db.execSQL(sql);
        return listCheck.getLink();
    }
    public boolean deleteLinkedNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "DELETE FROM " + TABLE_NOTE + " WHERE " + COLUMN_ID + " = " + id;

        db.execSQL(sql);
        return true;
    }
    public boolean deleteOne(assignment assignment)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        for (list_check l: assignment.getList_checks()){
            if(deleteLinkedNote(unlinkNote(l))){
                deleteOne(l);
            }
        }

        String sql = "DELETE FROM " + TABLE_ASSIGN + " WHERE " + COLUMN_ID + " = " + assignment.getId();

        db.execSQL(sql);
        return true;
    }
    public boolean updateOne(assignment assignment){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql =  "UPDATE FROM " + TABLE_ASSIGN
                + " SET " + COLUMN_TITLE + " = \"" + assignment.getTitle() + "\", "
                + COLUMN_TIME_START + " = \"" + assignment.getTimeStart() + "\", "
                + COLUMN_TIME_END + " = \"" + assignment.getTimeEnd() + "\", "
                + COLUMN_CHECKED + " = " + assignment.getDone()
                + " WHERE " + COLUMN_ID + " = " + assignment.getId();

        db.execSQL(sql);
        return true;
    }

//    public boolean addOne(Meet meet)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "INSERT INTO "+TABLE_MEET+" ("
//                +COLUMN_TITLE+","+COLUMN_TIME+","+COLUMN_LOCATION+","+COLUMN_LINK+","+COLUMN_ALERT+","+COLUMN_CHECKED+") VALUES(" +
//                meet.getTitle()+","+meet.getTime()+","+meet.getLocation()+","+meet.getLink()+","+meet.getAlert()+","+meet.getChecked()+")";
//        db.execSQL(sql);
//        return true;
//    }
//
//    public boolean addOne(Assign assign)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "INSERT INTO "+TABLE_ASSIGN+" ("+COLUMN_TITLE+","+COLUMN_TIME+","+COLUMN_CHECKED+") VALUES(" +
//                assign.getTitle()+","+assign.getTime()+","+assign.getChecked()+")";
//        db.execSQL(sql);
//        return true;
//    }
//
//    public boolean addOne(CheckList checkList)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "INSERT INTO "+TABLE_CHECKLIST+" ("+COLUMN_CONTENT+","+COLUMN_LINK_NOTE+","+COLUMN_CHECKED+") VALUES(" +
//                checkList.getContent()+","+checkList.getLinkNote()+","+checkList.getChecked()+")";
//        db.execSQL(sql);
//        return true;
//    }
}
