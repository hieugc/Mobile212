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
    public static final String TABLE_SUBJECT = "SUBJECT";
    public static final String COLUMN_START_DATE = "START_DATE";
    public static final String COLUMN_END_DATE = "END_DATE";
    public static final String COLUMN_STUDY_DAY = "STUDY_DAY";

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
                COLUMN_TA_NUMBER + " STRING," +
                COLUMN_TA_EMAIL + " TEXT," +
                COLUMN_NOTIFICATION + " INTEGER," +
                COLUMN_NOTIFICATION_TIME + " STRING," +
                COLUMN_TIMETABLE_TYPE + " INTEGER," +
                COLUMN_TIMETABLE_ID + " INTEGER)";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_SUBJECT + " (" +
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME+" STRING," +
                COLUMN_GROUP+" STRING," +
                COLUMN_LOCATION+" STRING," +
                COLUMN_START_DATE + " TEXT," +
                COLUMN_END_DATE + " TEXT," +
                COLUMN_START_TIME+" TEXT," +
                COLUMN_END_TIME+" TEXT," +
                COLUMN_STUDY_DAY + " TEXT," +
                COLUMN_TA_NAME+" STRING," +
                COLUMN_TA_NUMBER+" STRING," +
                COLUMN_TA_EMAIL+" STRING" +
                ")";

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

    public int getNewlyInsertedSubject()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_SUBJECT+" ORDER BY ID DESC LIMIT 1";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            return cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return 0;
    }

    public boolean addOne(Subject subject)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "INSERT INTO "+TABLE_SUBJECT+" (" +
                COLUMN_NAME+","+COLUMN_GROUP+","+COLUMN_LOCATION+","+COLUMN_START_DATE+","+COLUMN_END_DATE+","+COLUMN_START_TIME+","+COLUMN_END_TIME+","+COLUMN_STUDY_DAY+","+COLUMN_TA_NAME+","+COLUMN_TA_NUMBER+","+COLUMN_TA_EMAIL+") " +
                "VALUES(\""+subject.getClassName()+"\",\""+subject.getClassGroup()+"\",\""+subject.getClassRoom()+"\",\""+subject.getStartDate()+"\",\""+subject.getEndDate()+"\",\""+subject.getStartHour()+"\",\""+subject.getEndHour()+"\",\""+subject.getStudy()+"\",\""+subject.getLecturerName()+"\",\""+subject.getLecturerNumber()+"\",\""+subject.getLecturerMail()+"\")";
        db.execSQL(sql);
        return true;
    }

    public boolean addOne(TimeTable timeTable)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int noti;
        if(timeTable.getNotification())
            noti = 1;
        else
            noti = 0;

        String sql ="INSERT INTO "+TABLE_TIMETABLE+" ("
                +COLUMN_NAME+","+COLUMN_GROUP+","+COLUMN_LOCATION+","+COLUMN_DATE+","+COLUMN_START_TIME+","+COLUMN_END_TIME+","+COLUMN_TA_NAME+","+COLUMN_TA_NUMBER+","+COLUMN_TA_EMAIL+","+COLUMN_NOTIFICATION+","+COLUMN_NOTIFICATION_TIME+","+COLUMN_TIMETABLE_TYPE+","+COLUMN_TIMETABLE_ID+" ) " +
                "VALUES (\""+timeTable.getName()+"\",\""+timeTable.getGroup()+"\",\""+timeTable.getLocation()+"\",\""+timeTable.getDate()+"\",\""+timeTable.getStart_time()+"\",\""+timeTable.getEnd_time()+"\",\""+timeTable.getTA_name()+"\",\""+timeTable.getTA_number()+"\",\""+timeTable.getTA_email()+"\","+noti+",\""+timeTable.getNotification_time()+"\","+timeTable.getType()+","+timeTable.getTimetable_id()+")";

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

    public ArrayList<TimeTable> getTimetableByDate(String date)
    {
        ArrayList<TimeTable> tables = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_TIMETABLE+" WHERE "+COLUMN_DATE+" = \""+date+"\"";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String group = cursor.getString(2);
                String location = cursor.getString(3);
                String start_time = cursor.getString(5);
                String end_time = cursor.getString(6);
                String TA_name = cursor.getString(7);
                String TA_number = cursor.getString(8);
                String TA_email = cursor.getString(9);
                int noti = cursor.getInt(10);
                boolean notification;
                notification = noti == 1;
                String notification_time = cursor.getString(11);
                int type = cursor.getInt(12);
                int timetable_id = cursor.getInt(13);

                tables.add(new TimeTable(id, name, group, location, date, start_time, end_time, TA_name, TA_number, TA_email, notification, notification_time, type, timetable_id));

            }while(cursor.moveToNext());
        }
        else {
            //DO NOTHING
        }
        cursor.close();
        db.close();
        return tables;
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
        String sql = "UPDATE " + TABLE_NOTE + "\n"
                    +"SET " + COLUMN_TITLE + " = \"" + note.getTitle() + "\",\n"
                    +      COLUMN_CONTENT + " = \"" + note.getContent() + "\"\n"
                    +"WHERE " + COLUMN_ID + " = " + note.getId();
        Log.e("note",note.toString());
        Log.e("sql", sql);
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
                if (link == 0){
                    link = -1;
                }
                Log.e("link", String.valueOf(link));
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

        String sql =  "UPDATE " + TABLE_CHECKLIST
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
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
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
    public Note getNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NOTE + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            int id_res = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String date = cursor.getString(3);

            return new Note(id_res, title, content, date);
        }
        cursor.close();
        db.close();
        Log.e("NoteDB", String.valueOf(cursor) + " " + id);
        return null;
    }
    public ArrayList<Note> getOne(ArrayList<list_check> list_checks){
        ArrayList<Note> arrayList = new ArrayList<>();

        for (list_check l: list_checks){
            Log.e("check_li", String.valueOf(l.getLink()));
            if (l.getLink() != -1){
                SQLiteDatabase db = this.getReadableDatabase();
                String sql = "SELECT * FROM " + TABLE_NOTE + " WHERE " + COLUMN_ID + " = " + l.getLink();
                Cursor cursor = db.rawQuery(sql, null);
                Log.e("check_end", String.valueOf(cursor.getCount()));
                Log.e("check_end", String.valueOf(cursor));
                if(cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    int id_res = cursor.getInt(0);
                    Log.e("check_li1", String.valueOf(id_res));
                    String title = cursor.getString(1);
                    Log.e("check_li1", title);
                    String content = cursor.getString(2);
                    String date = cursor.getString(3);
                    arrayList.add(new Note(id_res, title, content, date));
                }
                cursor.close();
                db.close();
            }
            else{
                arrayList.add(null);
            }
        }
        return arrayList;
    }
    public int unlinkNote(list_check listCheck){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_CHECKLIST + " SET " + COLUMN_LINK_NOTE + " = NULL " + " WHERE " + COLUMN_ID + " = " + listCheck.getId();
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

        String sql =  "UPDATE " + TABLE_ASSIGN
                + " SET " + COLUMN_TITLE + " = \"" + assignment.getTitle() + "\", "
                + COLUMN_TIME_START + " = \"" + assignment.getTimeStart() + "\", "
                + COLUMN_TIME_END + " = \"" + assignment.getTimeEnd() + "\", "
                + COLUMN_CHECKED + " = " + assignment.getDone()
                + " WHERE " + COLUMN_ID + " = " + assignment.getId();

        db.execSQL(sql);
        return true;
    }

    public boolean updateOne(TimeTable timeTable)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int noti;
        if(timeTable.getNotification())
            noti = 1;
        else noti = 0;

        String sql = "UPDATE "+TABLE_TIMETABLE+" " +
                "SET "+COLUMN_NAME +" = \"" + timeTable.getName()+"\"," +
                COLUMN_GROUP+" = \""+timeTable.getGroup()+"\"," +
                COLUMN_LOCATION+" = \""+timeTable.getLocation()+"\"," +
                COLUMN_DATE+" = \""+timeTable.getDate()+"\"," +
                COLUMN_START_TIME+" =\""+timeTable.getStart_time()+"\"," +
                COLUMN_END_TIME+" =\""+timeTable.getEnd_time()+"\"," +
                COLUMN_TA_NAME+" =\""+timeTable.getTA_name()+"\"," +
                COLUMN_TA_NUMBER+" =\""+timeTable.getTA_number()+"\"," +
                COLUMN_TA_EMAIL+" =\""+timeTable.getTA_email()+"\"," +
                COLUMN_NOTIFICATION+" ="+noti+"," +
                COLUMN_NOTIFICATION_TIME+" =\""+timeTable.getNotification_time()+"\"," +
                COLUMN_TIMETABLE_TYPE+" =\""+timeTable.getType()+"\"," +
                COLUMN_TIMETABLE_ID+" =\""+timeTable.getTimetable_id()+"\"" +
                " WHERE "+COLUMN_ID+" = "+timeTable.getId();
        db.execSQL(sql);
        return true;
    }

    public ArrayList<TimeTable> getTimeTablesByForeignID(int timetable_id)
    {
        ArrayList<TimeTable> timeTables = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM "+TABLE_TIMETABLE+" WHERE "+COLUMN_TIMETABLE_TYPE+" = 1 AND "+COLUMN_TIMETABLE_ID+" = "+timetable_id;

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String group = cursor.getString(2);
                String location = cursor.getString(3);
                String date = cursor.getString(4);
                String start_time = cursor.getString(5);
                String end_time = cursor.getString(6);
                String TA_name = cursor.getString(7);
                String TA_number = cursor.getString(8);
                String TA_email = cursor.getString(9);
                int noti = cursor.getInt(10);
                boolean notification;
                notification = noti == 1;
                String notification_time = cursor.getString(11);
                int type = cursor.getInt(12);

                timeTables.add(new TimeTable(id, name, group, location, date, start_time, end_time, TA_name, TA_number, TA_email, notification, notification_time, type , timetable_id));

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return timeTables;
    }
}
