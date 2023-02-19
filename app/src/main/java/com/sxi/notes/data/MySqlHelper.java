package com.sxi.notes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sxi.notes.data.model.Notes;
import com.sxi.notes.data.model.Tasks;

public class MySqlHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final String NOTES_DB = "notes.db";

    private static final int DB_VERSION = 1;

    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private static final String FOLDER = "folder";
    private static final String DATE = "date";
    private static final String EDIT = "edit";
    private static final String THEME = "theme";
    private static final String REMINDER = "reminder";
    private static final String ISDONE = "isdone";


    public MySqlHelper(Context context) {
        super(context, NOTES_DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String noteQuery = "CREATE TABLE notes( title TEXT NOT NULL, text TEXT NOT NULL,folder TEXT NOT NULL,date LONG,edit LONG, theme INTEGER);";
        String taskQuery = "CREATE TABLE tasks( title TEXT NOT NULL, reminder LONG, isdone INTEGER);";
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
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, notes.getTitle());
        values.put(TEXT, notes.getText());
        values.put(FOLDER, notes.getFolder());
        values.put(DATE, notes.getDate());
        values.put(EDIT, notes.getEdit());
        values.put(THEME, notes.getTheme());
        db.insert("notes", null, values);
    }

    public Notes getNote(int id) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes", null);
        Notes notes = new Notes();
        if (cursor != null && cursor.moveToPosition(id)) {
            notes = new Notes(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4),
                    cursor.getInt(5)
            );
            cursor.close();
        }
        return notes;
    }

    public void updateNote(int id, Notes notes) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes", null);
        if (cursor != null && cursor.moveToPosition(id)) {
            Log.i("ddd", "onBackPressed: saved");
            String condition = "title= '" + cursor.getString(0) + "' AND " +
                    "text = '" + cursor.getString(1) + "' AND " +
                    "folder = '" + cursor.getString(2) + "' AND " +
                    "date = " + cursor.getLong(3) + " AND " +
                    "edit = " + cursor.getLong(4) + " AND " +
                    "theme = " + cursor.getInt(5);
            ContentValues values = new ContentValues();
            values.put(TITLE, notes.getTitle());
            values.put(TEXT, notes.getText());
            values.put(FOLDER, notes.getFolder());
            values.put(DATE, notes.getDate());
            values.put(EDIT, notes.getEdit());
            values.put(THEME, notes.getTheme());
            db.update("notes", values, condition, null);
            cursor.close();
        }
    }

    public void deleteNote(int id) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes", null);
        if (cursor != null && cursor.moveToPosition(id)) {
            String condition = "title= '" + cursor.getString(0) + "' AND " +
                    "text = '" + cursor.getString(1) + "' AND " +
                    "folder = '" + cursor.getString(2) + "' AND " +
                    "date = " + cursor.getLong(3) + " AND " +
                    "edit = " + cursor.getLong(4) + " AND " +
                    "theme = " + cursor.getInt(5);
            cursor.close();
            db.delete("notes", condition, null);
        }
    }

    public int getNoteSize() {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes", null);
        if (cursor != null) {
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    public void saveTask(Tasks tasks) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, tasks.getTitle());
        values.put(REMINDER, tasks.getReminder());
        values.put(ISDONE, tasks.getStatusInt());
        db.insert("tasks", null, values);
    }

    public Tasks getTask(int id) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor != null && cursor.moveToPosition(id)) {
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

    public void updateTask(int id, Tasks tasks) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor != null && cursor.moveToPosition(id)) {
            String condition = "title= '" + cursor.getString(0) + "' AND " +
                    "reminder=" + cursor.getLong(1) + " AND " +
                    "isdone=" + cursor.getInt(2);
            ContentValues values = new ContentValues();
            values.put(TITLE, tasks.getTitle());
            values.put(REMINDER, tasks.getReminder());
            values.put(ISDONE, tasks.getStatusInt());
            db.update("tasks", values, condition, null);
            cursor.close();
        }
    }

    public void deleteTask(int id) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor != null && cursor.moveToPosition(id)) {
            String condition = "title= '" + cursor.getString(0) + "' AND " +
                    "reminder=" + cursor.getLong(1) + " AND " +
                    "isdone=" + cursor.getInt(2);
            cursor.close();
            db.delete("tasks", condition, null);
        }
    }

    public int getTaskSize() {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor != null) {
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }
}
