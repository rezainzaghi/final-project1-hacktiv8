package com.hacktiv8.finalproject1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB_NOTE";
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_NOTE = "NOTE";
    private static final String NOTE_NAME = "note_name";
    private static final String DESCRIPTION = "description";

    public SQLiteDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreateSql = "CREATE TABLE " + TABLE_NOTE + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_NAME + " TEXT, " +
              DESCRIPTION + " TEXT )";

        System.out.println("Create Table: " + tableCreateSql);
        db.execSQL(tableCreateSql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NOTE);
        onCreate(db);
    }

    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTE_NAME,note.getTaskName());
        values.put(DESCRIPTION,note.getTaskDescription());

        db.insert(TABLE_NOTE, null, values);
        db.close();

    }

    public List<Note> getAllCountry(){

        List<Note> noteList = new ArrayList<>();
        //
        String queryAll = " SELECT * FROM "+TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryAll, null);

        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setTaskName(cursor.getString(1));
                note.setTaskDescription(cursor.getString(2));

             noteList.add(note);
            }
            while (cursor.moveToNext());
        }

        //
        return noteList;

    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, NOTE_NAME + " = ?", new String[]{note.getTaskName()});
        db.close();
    }



    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DESCRIPTION, note.getTaskDescription());

        return db.update(TABLE_NOTE, contentValues, NOTE_NAME + " = ?", new String[]{note.getTaskName()});
    }

}
