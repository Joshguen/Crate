package com.example.crate;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "CRAFTS029302390239";

    // Table columns
    public static final String _ID = "_id";
    public static final String SUBJECT = "subject";
    public static final String DESC = "description";
    public static final String STARTDATE = "start_date";
    public static final String ENDDATE = "end_date";

    // Database Information
    static final String DB_NAME = "JOURNALDEV_CRAFTS3333333.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SUBJECT + " TEXT NOT NULL, "
            + DESC + " TEXT, "
            + STARTDATE + " TEXT, "
            + ENDDATE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
