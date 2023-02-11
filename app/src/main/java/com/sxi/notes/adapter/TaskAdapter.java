package com.sxi.notes.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskVH> {
    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TaskVH extends RecyclerView.ViewHolder {
        public TaskVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
