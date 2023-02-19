package com.sxi.notes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sxi.notes.data.model.Notes;
import com.sxi.notes.data.model.Tasks;

import java.util.ArrayList;
import java.util.List;

public class MySqlHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqdb;
    private SQLiteDatabase db2;
    Context context;
    private static final String NOTES_DB = "notes.db";

    private static final int DB_VERSION = 1;

    private static final String TEXT = "text";
    private static final String TITLE = "title";
    private static final String FOLDER = "text";
    private static final String DATE = "date";
    private static final String EDIT = "edit";
    private static final String THEME = "theme";

    // Task contents
    private static final String TASK_TB = "tasks";
    private static final String REMINDER = "reminder";
    private static final String ISDONE = "isdone";
    private static final String SUBTASK = "subtask";


    public MySqlHelper(Context context) {
        super(context, NOTES_DB, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String noteQuery = "CREATE TABLE notes( title TEXT, text TEXT,date LONG,edit LONG, theme INTEGER);";
        String taskQuery = "CREATE TABLE tasks( title TEXT, reminder LONG, isdone INTEGER);";
        sqLiteDatabase.execSQL(noteQuery);
        sqLiteDatabase.execSQL(taskQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(sqLiteDatabase);
    }

    // Professional mode

    public void saveNote(Notes notes) {
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, notes.getTitle());
        values.put(TEXT, notes.getText());
        values.put(DATE, notes.getDate());
        values.put(EDIT, notes.getEdit());
        values.put(THEME, notes.getTheme());
        sqdb.insert("notes", null, values);
    }

    public Notes getNote(int id) {
        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM notes", null);
        Notes notes = new Notes();
        if (cursor != null && cursor.moveToPosition(id)) {
            notes = new Notes(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getLong(3),
                    cursor.getInt(4)
            );
            cursor.close();
        }
        return notes;
    }

    public void updateNote(int id, Notes notes) {
        sqdb = this.getWritableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM notes", null);
        if (cursor != null && cursor.moveToPosition(id)) {
            String condition = "title= '" + cursor.getString(0) +"' AND "+
                    "text = '" + cursor.getString(1) +"' AND "+
                    "date = " + cursor.getLong(2) +" AND "+
                    "edit = " + cursor.getLong(3) +" AND "+
                    "theme = " + cursor.getInt(4);
            ContentValues values = new ContentValues();
            values.put(TITLE,cursor.getString(0));
            values.put(TEXT,cursor.getString(1));
            values.put(DATE,cursor.getString(2));
            values.put(EDIT,cursor.getString(3));
            values.put(THEME,cursor.getString(4));
            sqdb.update("notes",values,condition,null);
            cursor.close();
        }
    }

    public void deleteNote(int id) {
        sqdb = this.getWritableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM notes", null);
        if (cursor != null && cursor.moveToPosition(id)) {
            String condition = "title= '" + cursor.getString(0) +"' AND "+
                    "text = '" + cursor.getString(1) +"' AND "+
                    "date = " + cursor.getLong(2) +" AND "+
                    "edit = " + cursor.getLong(3) +" AND "+
                    "theme = " + cursor.getInt(4);
            cursor.close();
            sqdb.delete("notes", condition, null);
        }
    }

    public int getNoteSize() {
        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM notes",null);
        if (cursor!=null){
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    public void saveTask(Tasks tasks){
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE,tasks.getTitle());
        values.put(REMINDER,tasks.getReminder());
        values.put(ISDONE,tasks.getStatusInt());
        sqdb.insert("tasks",null,values);
    }

    public Tasks getTask(int id){
        sqdb = this.getWritableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM tasks",null);
        if (cursor!=null&&cursor.moveToPosition(id)){
            Tasks tasks = new Tasks(
                    cursor.getString(0),
                    cursor.getLong(1),
                    cursor.getInt(2)
            );
            cursor.close();
            return tasks;
        }
        return null;
    }

    public void updateTask(int id,Tasks tasks){
        sqdb = this.getWritableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM tasks", null);
        if (cursor != null && cursor.moveToPosition(id)) {
            String condition = "title= '" + cursor.getString(0) +"' AND "+
                    "reminder=" + cursor.getLong(1) +" AND "+
                    "isdone=" + cursor.getInt(2);
            ContentValues values = new ContentValues();
            values.put(TITLE,cursor.getString(0));
            values.put(REMINDER,cursor.getString(1));
            values.put(ISDONE,cursor.getString(2));
            sqdb.update("tasks",values,condition,null);
            cursor.close();
        }
    }

    public void deleteTask(int id){
        sqdb = this.getWritableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM tasks", null);
        if (cursor != null && cursor.moveToPosition(id)) {
            String condition = "title= '" + cursor.getString(0) +"' AND "+
                    "reminder=" + cursor.getLong(1) +" AND "+
                    "isdone=" + cursor.getInt(2);
            cursor.close();
            sqdb.delete("tasks", condition, null);
        }
    }

    public int getTaskSize(){
        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM tasks",null);
        if (cursor!=null){
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }
}
