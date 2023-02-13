package com.sxi.notes.model;

import androidx.annotation.Nullable;

public class TaskModel {
    private String title;
    private long reminder;
    private SubTaskModel subTask;
    private boolean isDone;

    public TaskModel() {
    }

    public TaskModel(String title, long reminder, @Nullable SubTaskModel subTask, boolean isDone) {
        this.title = title;
        this.reminder = reminder;
        this.subTask = subTask;
        this.isDone = isDone;
    }
    public TaskModel(String title, long reminder, @Nullable String subTask, boolean isDone) {
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

    public SubTaskModel getSubTask() {
        return subTask;
    }

    public boolean isDone() {
        return isDone;
    }
}
