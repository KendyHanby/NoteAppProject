package com.sxi.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;

import com.sxi.notes.R;

public class TaskAdapter extends BaseExpandableListAdapter {

    private final Context context;

    public TaskAdapter(Context requireContext) {
        context = requireContext;
    }

    @Override
    public int getGroupCount() {
        return 5;
    }

    @Override
    public int getChildrenCount(int i) {
        return 3;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View group = view;
        if (view == null) {
            group = LayoutInflater.from(context).inflate(R.layout.list_task_item, viewGroup, false);
        }
        return group;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View child = view;
        if (view == null) {
            child = LayoutInflater.from(context).inflate(R.layout.list_subtask_item, viewGroup, false);
        }
        return child;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
