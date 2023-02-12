package com.sxi.notes.model;

import androidx.annotation.Nullable;

public class TaskModel {
    private String title,text;
    private long reminder;
    private SubTaskModel subTask;
    private boolean isDone;

    public TaskModel() {
    }

    public TaskModel(String title, String text, long reminder, @Nullable SubTaskModel subTask, boolean isDone) {
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

    public SubTaskModel getSubTask() {
        return subTask;
    }

    public boolean isDone() {
        return isDone;
    }
}
