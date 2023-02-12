package com.sxi.notes.data;

import android.content.Context;

import com.sxi.notes.MySqlHelper;
import com.sxi.notes.data.model.NoteModel;

import java.util.ArrayList;

public class NoteList extends ArrayList<NoteModel> {
    private MySqlHelper db;

    public NoteList() {
    }

    public NoteList(Context context) {
         db = new MySqlHelper(context);
    }

    public NoteList(int initialCapacity) {
        super(initialCapacity);
    }
    public void addNote(NoteModel noteModel){
        db.saveNote(noteModel);
        add(noteModel);
    }

    @Override
    public void add(int index, NoteModel element) {
        db.editNote(index+1,element);
        super.add(index, element);
    }

    @Override
    public NoteModel remove(int index) {
        db.deleteNote(index+1);
        return super.remove(index);
    }
}
