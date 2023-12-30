package com.example.coffeetech;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MonthlyReportDB";
    private static final int DATABASE_VERSION = 1;

    // Define your table and columns
    private static final String TABLE_NAME = "monthly_report";
    private static final String COLUMN_DISEASE_NAME = "disease_name";
    private static final String COLUMN_PERCENTAGE = "percentage";
    private static final String COLUMN_DATE_TIME = "date_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_DISEASE_NAME + " TEXT, " +
                COLUMN_PERCENTAGE + " REAL, " +
                COLUMN_DATE_TIME + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle upgrades if needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
