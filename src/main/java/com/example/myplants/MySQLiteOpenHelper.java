package com.example.myplants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    // constructor
    public MySQLiteOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // get each values when called
// TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        // create SQL table with each columns
        String sql = "create table myPlantList (" +
                "name text, " +
                "size text, " +
                "level text, " +
                "feature text, " +
                "watering text, " +
                "nickname text, " +
                "date text, "+
                "temperature text, "+
                "caution text);";
        db.execSQL(sql);
    }
    @Override
    // if student table is already exist
    // make modification
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists myPlant";
        db.execSQL(sql);
        onCreate(db);
    }
}