package com.sxi.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqdb;
    Context context;
        private static final String DB_NAME = "Mi_NoteDB";
        private static final int DB_Version = 1;
        private static final String TEXT = "text";
        private static  final String TB_NAME = "MiNote";
        private static  final String TEXT_ID = "id";
    public MySqlHelper(Context context) {

        super(context, DB_NAME, null, DB_Version);
        this.context = context;
    }

    public void dbOpen(){
        MySqlHelper helper = new MySqlHelper(context);
        sqdb = helper.getWritableDatabase();
    }
    public void dbClose(){
        sqdb.close();
    }

    public Long dataInsert(String textdata){
        ContentValues cv = new ContentValues();
        cv.put(TEXT,textdata);
        Long id = sqdb.insert(TB_NAME,null,cv);
        return id;
    }
    public String dataQuery(){
        String [] columns = {TEXT_ID,TEXT};
        String str = null;
        Cursor c = sqdb.query(TB_NAME,columns,null,null,null,null,null);
        for(c.moveToFirst();c.isAfterLast();c.moveToNext()){

            int idIndex = c.getColumnIndex(TEXT_ID);
            int textIndex = c.getColumnIndex(TEXT);
            str = c.getString(textIndex);
        }
        return str;
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TB_NAME+"("+TEXT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TEXT_ID+" TEXT NOT NULL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(sqLiteDatabase);

    }
}
