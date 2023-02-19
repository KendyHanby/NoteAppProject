package com.sxi.notes.data.model;

public class Notes {
    private String title, text,folder;
    private long date,edit;
    private int theme;

    public Notes() {
    }

    public Notes(String title, String text,String folder, long date, long edit, int theme) {
        this.title = title;
        this.text = text;
        this.folder = folder;
        this.date = date;
        this.edit = edit;
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

    public long getEdit() {
        return edit;
    }

    public int getTheme() {
        return theme;
    }
}
