package com.app.timetable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

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
    private static final String COLUMN_LINK_ASSIGN = "LINK_" + TABLE_ASSIGN;

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
                + COLUMN_CHECKED + " BOOL )";
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

    public boolean addOne()
    {

        return true;
    }


}
