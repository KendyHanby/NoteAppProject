package com.sxi.notes.model;

import androidx.annotation.Nullable;

import java.util.List;

public class TaskModel {
    private String title;
    private long reminder;
    private boolean isDone;

    public TaskModel() {
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

    public boolean isDone() {
        return isDone;
    }
}
