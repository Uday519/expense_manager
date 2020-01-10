package com.example.expenses.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import java.io.File;
import java.util.Date;
import java.util.List;

public class DbManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "allexpenses";
    private File DB_FILE;

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DB_FILE = context.getDatabasePath(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + "id" + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + "day" + " INTEGER NOT NULL,\n" +
                "    " + "month" + " INTEGER NOT NULL,\n" +
                "    " + "year" + " INTEGER NOT NULL,\n" +
                "    " + "item" + " VARCHAR(200) NOT NULL,\n" +
                "    " + "price" + " double NOT NULL\n" +
                ");";
        db.execSQL(sql);
    }

    private boolean checkDataBase() {
        return DB_FILE.exists();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean addNewEXpense(Double price, String item){

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());

        Date currentTime = localCalendar.getTime();
        int currentDay = localCalendar.get(Calendar.DATE);
        int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
        int currentYear = localCalendar.get(Calendar.YEAR);

        ContentValues contentValues = new ContentValues();
        contentValues.put("item", item);
        contentValues.put("price",price);
        contentValues.put("day",currentDay);
        contentValues.put("month",currentMonth);
        contentValues.put("year",currentYear);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME,null, contentValues) != -1;

    }

    public Cursor getALLItems(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC", null);
    }

    public Cursor searchItem(String data){
        SQLiteDatabase db = getReadableDatabase();
        String[] splitvalues = data.split("-");
        return db.rawQuery("SELECT * FROM allexpenses WHERE day = ? AND month = ? AND year = ?", splitvalues);
    }

    public Cursor getCost(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT day,month,year,SUM(price) as cost FROM allexpenses GROUP BY day Order BY id DESC LIMIT 7;",null);
    }

    public boolean updateNewEXpense(Double price, String item, int id){

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());

        Date currentTime = localCalendar.getTime();
        int currentDay = localCalendar.get(Calendar.DATE);
        int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
        int currentYear = localCalendar.get(Calendar.YEAR);

        ContentValues contentValues = new ContentValues();
        contentValues.put("item", item);
        contentValues.put("price",price);
//        contentValues.put("day",currentDay);
//        contentValues.put("month",currentMonth);
//        contentValues.put("year",currentYear);
        SQLiteDatabase db = getWritableDatabase();
        return db.update(TABLE_NAME, contentValues, "id =?",new String[]{String.valueOf(id)}) == 1;

    }

    public boolean deleteEexpense(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, "id =?", new String[]{String.valueOf(id)}) == 1;
    }
}
