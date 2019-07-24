package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.data.TodoContract.TodoEntry;


/**
 * 3. db access object
 * it will contain CRUD methods
 */
public class DAO {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public DAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void openDb(){
        database = dbHelper.getWritableDatabase();
    }
    public void closeDb(){
        database.close();
    }
//5
    public void createRow(String title, String subTitle){

        ContentValues values = new ContentValues();
        values.put(TodoEntry.COLUMN_NAME_TITLE,title);
        values.put(TodoEntry.COLUMN_NAME_SUBTITLE,subTitle);

        database.insert(TodoEntry.TABLE_NAME,null,values);
        //insert into tablename(id,title,subtitle
        //database.rawQuery("insert into notes_table()")
    }

    //6
    public String readRow(){
        //database.rawQuery("select * from notes_table")
        Cursor cursor = database.query(TodoEntry.TABLE_NAME,null,null,
                null,null,null,null);

        cursor.moveToLast(); // pointing the cursor to last row

        int titleIndex = cursor.getColumnIndexOrThrow(TodoEntry.COLUMN_NAME_TITLE);
        int subtitleIndex = cursor.getColumnIndexOrThrow(TodoEntry.COLUMN_NAME_SUBTITLE);

        String title = cursor.getString(titleIndex);
        String subtitle = cursor.getString(subtitleIndex);

        return title +"\n" + subtitle;
    }

    public Cursor readRows(){
        return  database.query(TodoEntry.TABLE_NAME,null,null,
                null,null,null,null);
    }
    public void updateRow(){}
    public void deleteRow(){}

}
