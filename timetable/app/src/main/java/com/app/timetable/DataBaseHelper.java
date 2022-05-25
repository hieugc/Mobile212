package com.app.timetable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String TABLE_BKEL = "BKEL_ACC";
    public static final String COLUMN_USER = "USER";
    public static final String COLUMN_PWD = "PWD";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_CONTENT = "CONTENT";
    public static final String COLUMN_TIME = "TIME";
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
    public static final String STRING_SEMESTER = "\"212\"";
    public static final String SEMESTER = "212";
    private static final String COLUMN_LINK_ASSIGN = "LINK_" + TABLE_ASSIGN;
    public static final String COLUMN_CREATED_AT = "CREATED_AT";

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
                COLUMN_LOCATION+" STRING, " +
                COLUMN_DATE+" STRING, "+
                COLUMN_TIME+" STRING, " +
                COLUMN_WEEK + " TEXT, " +
                COLUMN_SEMESTER + " STRING, " +
                COLUMN_USERID + " INT, FOREIGN KEY("+COLUMN_USERID+") REFERENCES "+TABLE_BKEL+"("+COLUMN_ID+") )";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_NOTE
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " STRING, "
                + COLUMN_CONTENT + " TEXT)";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_MEET
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " STRING, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_LOCATION + " TEXT, "
                + COLUMN_LINK + " STRING, "
                + COLUMN_ALERT + " STRING,"
                + COLUMN_CHECKED + " BOOL )";
        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_ASSIGN
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " STRING, "
                + COLUMN_TIME + " TEXT,"
                + COLUMN_CHECKED + " BOOL)";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_CHECKLIST
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CONTENT + " TEXT, "
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

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean addOne(BKTimeTable bkTimeTable)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "INSERT INTO "+TABLE_BK_TIMETABLE+" ("
                +COLUMN_NAME+","+COLUMN_LOCATION+","+COLUMN_DATE+","+COLUMN_TIME+","+COLUMN_WEEK+","+COLUMN_SEMESTER+","+COLUMN_USERID+") " +
                "VALUES(\""+bkTimeTable.getName()+"\",\""+bkTimeTable.getLocation()+"\",\""+bkTimeTable.getDate()+"\",\""+bkTimeTable.getTime()+"\",\""+bkTimeTable.getWeek()+"\",\""+bkTimeTable.getSemester()+"\","+bkTimeTable.getUserID()+")";

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
                String location = cursor.getString(2);
                String date = cursor.getString(3);
                String time =cursor.getString(4);
                String week = cursor.getString(5);

                BKTimeTable bkTimeTable = new BKTimeTable(id, name, location, date, time, week, SEMESTER, userID);

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
