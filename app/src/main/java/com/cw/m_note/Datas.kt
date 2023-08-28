package com.cw.m_note

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

data class NoteModel(var id: Int?, var title: String, var content: String, var date: Double)
data class TaskModel(var id: Int, var title: String, var status: Boolean, var date: Double)
class Database(private val context: Context) : SQLiteOpenHelper(
    context,
    "database.db",
    null,
    1
) {
    private lateinit var db: SQLiteDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS note (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT, content BLOB, date REAL);")
        db?.execSQL("CREATE TABLE IF NOT EXISTS task (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT, status INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("")
    }

    fun insert(noteModel: NoteModel) {
        db = this.writableDatabase
        val value = ContentValues()
        value.put("title", noteModel.title)
        value.put("content", noteModel.content)
        value.put("date", noteModel.date)
        db.insert("note", null, value)
    }

    fun insert(taskModel: TaskModel) {
        db = this.writableDatabase
        val value = ContentValues()
        value.put("title", taskModel.title)
        value.put("content", taskModel.status)
        value.put("date", taskModel.date)
        db.insert("task", null, value)
    }

    fun editNote(noteModel: NoteModel) {
        db = this.writableDatabase
        val value = ContentValues()
        value.put("title", noteModel.title)
        value.put("content", noteModel.content)
        db.update("note", value, "id=${noteModel.id}", null)
    }

    fun getNote(position: Int, id: Int = -1): NoteModel {
        db = this.readableDatabase
        val c = db.rawQuery(
            if (position == -1) "SELECT * FROM note WHERE id=$id;" else "SELECT * FROM note;",
            null
        )
        if (position != -1) {
            c.moveToPosition(position)
        } else {
            c.moveToNext()
        }
        val noteModel = NoteModel(
            c.getInt(0),
            c.getString(1),
            c.getString(2),
            c.getDouble(3)
        )
        c.close()
        return noteModel
    }

    fun noteSize(): Int {
        db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM note;", null)
        val size = c.count
        c.close()
        return size
    }

    fun delete(noteModel: NoteModel) {
        db = this.writableDatabase
        db.delete("note", "id=?", arrayOf("${noteModel.id}"))
    }

}