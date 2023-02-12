package com.sxi.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sxi.notes.data.NoteList;
import com.sxi.notes.data.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class MySqlHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqdb;
    Context context;
    private static final String NOTES_DB = "notes.db";

    private static final int DB_VERSION = 1;
    private static final String TEXT = "text";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String FOLDER = "text";
    private static final String DATE = "date";
    private static final String THEME = "theme";


    public MySqlHelper(Context context) {
        super(context, NOTES_DB, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String noteQuery = "CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, text TEXT,date LONG, theme INTEGER);";
        String taskQuery = "CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, task TEXT,reminder long);";
        sqLiteDatabase.execSQL(noteQuery);
        sqLiteDatabase.execSQL(taskQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(sqLiteDatabase);
    }

    /**
     * Save new note to database
     */
    public long saveNote(NoteModel model) {
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, model.getTitle());
        values.put(TEXT, model.getText());
        values.put(DATE, model.getDate());
        values.put(THEME, model.getTheme());
        return sqdb.insert("notes", null, values);
    }

    public long editNote(long id, NoteModel model) {
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, model.getTitle());
        values.put(TEXT, model.getText());
        values.put(DATE, model.getDate());
        values.put(THEME, model.getTheme());
        return sqdb.update("notes", values, "id=" + id, null);
    }

    /**
     * Delete note from database
     */
    public void deleteNote(long id) {
        sqdb = this.getWritableDatabase();
        sqdb.delete("notes", "id=" + id, null);
    }

    public NoteList getNotes() {
        sqdb = this.getReadableDatabase();
        NoteList list = new NoteList();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM notes", null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(1),
                    text = cursor.getString(2);
            long date = cursor.getLong(3);
            int theme = cursor.getInt(4);
            list.add(new NoteModel(title, text, date, theme));
        }
        cursor.close();
        return list;
    }

    public NoteModel getNote(long id){
        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM notes WHERE id="+(id+1),null);
        NoteModel noteModel = new NoteModel();
        if (cursor!=null&&cursor.moveToNext()){
            noteModel = new NoteModel(cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getInt(4));
            cursor.close();
        }
        return noteModel;
    }

    public int getNoteSize(){
        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT id FROM notes",null);
        if (cursor!=null) {
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }
}
