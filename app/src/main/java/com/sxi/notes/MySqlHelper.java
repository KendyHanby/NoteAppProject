package com.sxi.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sxi.notes.model.NoteModel;
import com.sxi.notes.model.TaskModel;

public class MySqlHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqdb;
    Context context;
    private static final String NOTES_DB = "notes.db";

    private static final int DB_VERSION = 1;

    // Notes contents
    private static final String NOTE_TB = "notes";
    private static final String ID = "id";
    private static final String TEXT = "text";
    private static final String TITLE = "title";
    private static final String FOLDER = "text";
    private static final String DATE = "date";
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
        String noteQuery = "CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, text TEXT,date LONG, theme INTEGER);";
        String taskQuery = "CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, reminder LONG, isdone INTEGER);";
        sqLiteDatabase.execSQL(noteQuery);
        sqLiteDatabase.execSQL(taskQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(sqLiteDatabase);
    }

    // This is the start of note section

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

    /**
     * Edit notes in database
     */
    public long updateNote(long id, NoteModel model) {
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, model.getTitle());
        values.put(TEXT, model.getText());
        values.put(DATE, model.getDate());
        values.put(THEME, model.getTheme());
        return sqdb.update("notes", values, "id=" + (id + 1), null);
    }

    /**
     * Delete note from database
     */
    public void deleteNote(long id) {
        sqdb = this.getWritableDatabase();
        sqdb.delete("notes", "id=" + id, null);
    }

    /**
     * Get Note from database
     */
    public NoteModel getNote(long id) {
        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM notes WHERE id=" + (id + 1), null);
        NoteModel noteModel = new NoteModel();
        if (cursor != null && cursor.moveToFirst()) {
            noteModel = new NoteModel(cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getInt(4));
            cursor.close();
        }
        return noteModel;
    }

    /**
     * Get Note size in database
     */
    public int getNoteSize() {
        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT id FROM notes", null);
        if (cursor != null) {
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    // This is the end of note section

    // This is the start of task section

    /**
     * Save task to database
     */
    public long saveTask(TaskModel taskModel) {
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, taskModel.getTitle());
        values.put(REMINDER, taskModel.getReminder());
        values.put(ISDONE, taskModel.isDoneInt());
        return sqdb.insert(TASK_TB, null, values);
    }

    public TaskModel getTask(long id) {

        // TODO : unfinished

        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT * FROM tasks WHERE id=" + (id + 1), null);
        if (cursor != null && cursor.moveToFirst()) {
            TaskModel taskModel = new TaskModel(
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getInt(3)
            );
            cursor.close();
            return taskModel;
        }
        return null;
    }

    public long updateTask(long id, TaskModel taskModel) {
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, taskModel.getTitle());
        values.put(REMINDER, taskModel.getReminder());
        values.put(ISDONE, taskModel.isDone());
        return sqdb.update(TASK_TB, values, "id=" + (id + 1), null);
    }

    public int getTaskSize() {
        sqdb = this.getReadableDatabase();
        Cursor cursor = sqdb.rawQuery("SELECT id FROM tasks", null);
        if (cursor != null) {
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }
}
