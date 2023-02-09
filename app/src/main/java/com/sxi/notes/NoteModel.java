package com.sxi.notes;

public class NoteModel {
    private String title,text,folder;
    private long date;
    private int theme;

    public NoteModel() {
    }

    public NoteModel(String title, String text, String folder, long date, int theme) {
        this.title = title;
        this.text = text;
        this.folder = folder;
        this.date = date;
        this.theme = theme;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getFolder() {
        return folder;
    }

    public long getDate() {
        return date;
    }

    public int getTheme() {
        return theme;
    }
}
