package com.sxi.notes.data.model;

public class TaskModel {
    private String title,text;
    private long reminder;
    private int subTask;
    private boolean isDone;

    public TaskModel() {
    }

    public TaskModel(String title, String text, long reminder, int subTask, boolean isDone) {
        this.title = title;
        this.text = text;
        this.reminder = reminder;
        this.subTask = subTask;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getReminder() {
        return reminder;
    }

    public int getSubTask() {
        return subTask;
    }

    public boolean isDone() {
        return isDone;
    }
}
