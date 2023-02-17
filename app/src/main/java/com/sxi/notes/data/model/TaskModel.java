package com.sxi.notes.data.model;

public class TaskModel {
    private String title;
    private long reminder;
    private boolean isDone;

    public TaskModel() {
    }

    public TaskModel(String title, long reminder, int isDone) {
        this.title = title;
        this.reminder = reminder;
        this.isDone = isDone==1;
    }

    public TaskModel(String title, long reminder, boolean isDone) {
        this.title = title;
        this.reminder = reminder;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public long getReminder() {
        return reminder;
    }

    public boolean getStatus() {
        return isDone;
    }

    public int getStatusInt(){
        return isDone?1:0;
    }
}
