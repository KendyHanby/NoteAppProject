package com.sxi.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskVH> {
    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class TaskVH extends RecyclerView.ViewHolder {
        CheckBox check;
        TextView title;
        public TaskVH(@NonNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.task_check);
            title = itemView.findViewById(R.id.task_title);
        }
    }
}
