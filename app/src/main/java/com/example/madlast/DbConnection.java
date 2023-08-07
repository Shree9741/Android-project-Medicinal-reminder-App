package com.example.madlast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MedTable";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "MedTable";
    private static final String COLUMN_MEDICINE_NAME = "medicineName";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";

    public DbConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_MEDICINE_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
    }

    public boolean insertValues(String medName, String medDate, String medTime) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MEDICINE_NAME, medName);
        contentValues.put(COLUMN_DATE, medDate);
        contentValues.put(COLUMN_TIME, medTime);

        long result = database.insert(TABLE_NAME, null, contentValues);
        return (result != -1);
    }

    public Cursor retrieveData(String date, String time) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] selectionArgs = {date, time};
        String selection = COLUMN_DATE + "=? AND " + COLUMN_TIME + "=?";
        Cursor cursor = database.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        return cursor;
    }
}
