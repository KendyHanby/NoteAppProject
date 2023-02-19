package com.sxi.notes.data.model;

public class Notes {
    private String title, text;
    private long date,edit;
    private int theme;

    public Notes() {
    }

    public Notes(String title, String text, long date, long edit, int theme) {
        this.title = title;
        this.text = text;
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
