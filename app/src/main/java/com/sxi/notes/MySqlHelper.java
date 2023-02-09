package com.sxi.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "Mi_NoteDB";
        private static final int DB_Version = 1;
        private static final String TEXT = "text";
        private static  final String TB_NAME = "MiNote";
        private static  final String TEXT_ID = "id";
    public MySqlHelper(Context context) {
        super(context, DB_NAME, null, DB_Version);
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
