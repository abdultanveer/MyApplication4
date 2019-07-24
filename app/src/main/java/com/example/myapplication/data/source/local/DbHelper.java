package com.example.myapplication.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myapplication.data.source.local.TodoContract.TodoEntry;

/**
 *2. This class helps me create a db and its table
 *
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoEntry.TABLE_NAME + " (" +
                    TodoEntry._ID + " INTEGER PRIMARY KEY," +
                    TodoEntry.COLUMN_NAME_TITLE + " TEXT," +
                    TodoEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    public DbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * this lifecycle method gets called for the first time when an instance of dbhelper is created
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        //CREATE TABLE notes_table (_id INTEGER PRIMARY KEY,title TEXT,subtitle TEXT)"
        database.execSQL(SQL_CREATE_ENTRIES);
    }


    /**
     * i
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // when i upgrade from ver 1 to ver 2 , i want to add a new col to the table
        //i.e a timestamp col

    }
}
