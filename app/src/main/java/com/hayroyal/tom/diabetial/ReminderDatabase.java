package com.hayroyal.tom.diabetial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class ReminderDatabase extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ReminderDatabase";

    // Table name
    private static final String TABLE_REMINDERS = "ReminderTable";
    private static final String TABLE_BLOODSUGAR = "BloodSugar";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_REPEAT = "repeat";
    private static final String KEY_REPEAT_NO = "repeat_no";
    private static final String KEY_REPEAT_TYPE = "repeat_type";
    private static final String KEY_ACTIVE = "active";

    private static String D_ID = "id";
    private static final String D_TITLE = "title";
    private static String D_Bloodpressure = "bloodpressure";
    private static String D_Bloodsugar = "bloodsugar";
    private static String D_Cholesterol = "cholestrol";
    private static String D_Bodyweight = "bodyweight";
    private static final String D_TIME = "time";
    private static String D_DATE = "date";
    private static final String D_ACTIVE = "active";

    public ReminderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " INTEGER,"
                + KEY_REPEAT + " BOOLEAN,"
                + KEY_REPEAT_NO + " INTEGER,"
                + KEY_REPEAT_TYPE + " TEXT,"
                + KEY_ACTIVE + " BOOLEAN" + ")";

        String CREATE_BLOODSUGAR_TABLE = "CREATE TABLE " + TABLE_BLOODSUGAR +
                "("
                + D_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + D_TITLE + " TEXT NOT NULL,"
                + D_DATE + " TEXT NOT NULL,"
                + D_TIME + " INTEGER NOT NULL,"
                + D_Bloodpressure + "TEXT NOT NULL,"
                + D_Bloodsugar + " TEXT NOT NULL,"
                + D_Cholesterol + " TEXT NOT NULL,"
                + D_Bodyweight + " TEXT NOT NULL,"
                + D_ACTIVE + " BOOLEAN NOT NULL" + ")";

        db.execSQL(CREATE_REMINDERS_TABLE);
        db.execSQL(CREATE_BLOODSUGAR_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOODSUGAR);

        // Create tables again
        onCreate(db);
    }

    // Adding new Reminder
    public int addReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE , reminder.getTitle());
        values.put(KEY_DATE , reminder.getDate());
        values.put(KEY_TIME , reminder.getTime());
        values.put(KEY_REPEAT , reminder.getRepeat());
        values.put(KEY_REPEAT_NO , reminder.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(KEY_ACTIVE, reminder.getActive());

        // Inserting Row
        long ID = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return (int) ID;
    }

    public int addTestResult (TestResult testResult){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(D_TITLE, testResult.getTitle());
        values.put(D_DATE, testResult.getBloodsugar());
        values.put(D_TIME, testResult.getCholesterol());
        values.put(D_Bloodpressure, testResult.getBodyweight());
        values.put(D_Bloodsugar , testResult.getDate());
        values.put(D_Cholesterol , testResult.getBloodpressure());
        values.put(D_Bodyweight, testResult.getTime());
        values.put(D_ACTIVE, testResult.getActive());
        Log.e("DataSaved", testResult.toString());

        // Inserting Row
        long ID = db.insert(TABLE_BLOODSUGAR, null, values);
        db.close();
        return (int) ID;
    }


    // Getting single Reminder
    public Reminder getReminder(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_TITLE,
                                KEY_DATE,
                                KEY_TIME,
                                KEY_REPEAT,
                                KEY_REPEAT_NO,
                                KEY_REPEAT_TYPE,
                                KEY_ACTIVE
                        }, KEY_ID + "=?",

                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Reminder reminder = new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));

        return reminder;
    }

    public TestResult getTestResult(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BLOODSUGAR, new String[]
                        {
                                D_ID,
                                D_TITLE,
                                D_DATE,
                                D_TIME,
                                D_Bloodpressure,
                                D_Bloodsugar,
                                D_Cholesterol,
                                D_Bodyweight,
                                D_ACTIVE
                        }, KEY_ID + "=?",

                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        TestResult testResult = new TestResult(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(8));

        return testResult;
    }
    // Getting all Reminders
    public List<Reminder> getAllReminders(){
        List<Reminder> reminderList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Reminder reminder = new Reminder();
                reminder.setID(Integer.parseInt(cursor.getString(0)));
                reminder.setTitle(cursor.getString(1));
                reminder.setDate(cursor.getString(2));
                reminder.setTime(cursor.getString(3));
                reminder.setRepeat(cursor.getString(4));
                reminder.setRepeatNo(cursor.getString(5));
                reminder.setRepeatType(cursor.getString(6));
                reminder.setActive(cursor.getString(7));

                // Adding Reminders to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }

    // Getting Reminders Count
    public int getRemindersCount(){
        String countQuery = "SELECT * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    public List<TestResult> getAllTestResult(){
        List<TestResult> testresultList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_BLOODSUGAR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                TestResult testResult = new TestResult();
                testResult.setID(Integer.parseInt(cursor.getString(0)));
                testResult.setTitle(cursor.getString(1));
                testResult.setDate(cursor.getString(2));
                testResult.setTime(cursor.getString(3));
                testResult.setBloodpressure(cursor.getString(4));
                testResult.setBloodsugar(cursor.getString(5));
                testResult.setCholesterol(cursor.getString(6));
                testResult.setBodyweight(cursor.getString(7));
                testResult.setTitle(cursor.getString(8));

                // Adding Reminders to list
                testresultList.add(testResult);
            } while (cursor.moveToNext());
        }
        return testresultList;
    }


    // Updating single Reminder
    public int updateReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE , reminder.getTitle());
        values.put(KEY_DATE , reminder.getDate());
        values.put(KEY_TIME , reminder.getTime());
        values.put(KEY_REPEAT , reminder.getRepeat());
        values.put(KEY_REPEAT_NO , reminder.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(KEY_ACTIVE, reminder.getActive());

        // Updating row
        return db.update(TABLE_REMINDERS, values, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getID())});
    }

    public int updateTestResult(TestResult testResult){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(D_TITLE, testResult.getBloodpressure());
        values.put(D_DATE, testResult.getBodyweight());
        values.put(D_TIME, testResult.getBloodsugar());
        values.put(D_Bloodpressure, testResult.getDate());
        values.put(D_Bloodsugar, testResult.getTime());
        values.put(D_Cholesterol, testResult.getCholesterol());
        values.put(D_Bodyweight, testResult.getActive());
        values.put(D_TITLE, testResult.getTitle());
        Log.e("DataSaved", testResult.toString());
        // Updating row
        return db.update(TABLE_BLOODSUGAR, values, D_ID + "=?",
                new String[]{String.valueOf(testResult.getID())});
    }


    // Deleting single Reminder
    public void deleteReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getID())});
        db.close();
    }
    public void deleteTestResult(TestResult testResult){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BLOODSUGAR, D_ID + "=?",
                new String[]{String.valueOf(testResult.getID())});
        db.close();
    }
}