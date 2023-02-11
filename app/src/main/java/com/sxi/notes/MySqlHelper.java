package com.sxi.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sxi.notes.model.NoteModel;

public class MySqlHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqdb;
    Context context;
    private static final String DB_NAME = "Mi_NoteDB.db";
    private static final int DB_VERSION = 1;
    private static final String TEXT = "text";
    private static final String TB_NAME = "notes";
    private static final String TEXT_ID = "id";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String FOLDER = "text";
    private static final String DATE = "date";
    private static final String THEME = "theme";


    public MySqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, text TEXT,date LONG, theme INTEGER);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(sqLiteDatabase);
    }

    /**Save new note to database*/
    public long saveNote(NoteModel model){
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE,model.getTitle());
        values.put(TEXT,model.getText());
        values.put(DATE,model.getDate());
        values.put(THEME,model.getTheme());
        return sqdb.insert("notes",null,values);
    }

    public long editNote(long id,NoteModel model){
        sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE,model.getTitle());
        values.put(TEXT,model.getText());
        values.put(DATE,model.getDate());
        values.put(THEME,model.getTheme());
        return sqdb.update("notes",values,"id="+id,null);
    }

    /**Delete note from database*/
    public void deleteNote(long id){
        sqdb = this.getWritableDatabase();
        sqdb.delete("notes","id="+id,null);
    }

    /**
     * Don't need to use it
     */
    @Deprecated
    public void dbOpen() {
        MySqlHelper helper = new MySqlHelper(context);
        sqdb = helper.getWritableDatabase();
    }

    /**
     * Don't need to use it
     */
    @Deprecated
    public void dbClose() {
        sqdb.close();
    }

    /**
     * No enough data to save*/
    @Deprecated
    public Long dataInsert(String textdata) {
        ContentValues cv = new ContentValues();
        cv.put(TEXT, textdata);
        Long id = sqdb.insert(TB_NAME, null, cv);
        return id;
    }

    /**Too Complex to use*/
    @Deprecated
    public String dataQuery() {
        String[] columns = {TEXT_ID, TEXT};
        String str = null;
        Cursor c = sqdb.query(TB_NAME, columns, null, null, null, null, null);
        for (c.moveToFirst(); c.isAfterLast(); c.moveToNext()) {

            int idIndex = c.getColumnIndex(TEXT_ID);
            int textIndex = c.getColumnIndex(TEXT);
            str = c.getString(textIndex);
        }
        return str;
    }



}
