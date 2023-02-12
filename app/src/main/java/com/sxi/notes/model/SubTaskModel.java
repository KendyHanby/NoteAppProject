package com.sxi.notes.model;

public class SubTaskModel {
    private String title;
    private boolean isDone;

    public SubTaskModel() {
    }

    public SubTaskModel(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return isDone;
    }
}
