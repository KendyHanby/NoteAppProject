package com.sxi.notes.model;

import androidx.annotation.Nullable;

import java.util.List;

public class TaskModel {
    private String title;
    private long reminder;
    private boolean isDone;
    private List<SubTaskModel> subTask;

    public TaskModel() {
    }

    public TaskModel(String title, long reminder, @Nullable List<SubTaskModel> subTask, boolean isDone) {
        this.title = title;
        this.reminder = reminder;
        this.subTask = subTask;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public long getReminder() {
        return reminder;
    }


    public List<SubTaskModel> getSubTask() {
        return subTask;
    }

    public boolean isDone() {
        return isDone;
    }
}
