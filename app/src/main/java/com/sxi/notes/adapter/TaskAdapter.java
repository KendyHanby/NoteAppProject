package com.sxi.notes.adapter;

import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        holder.check.setOnCheckedChangeListener((compoundButton, b) -> {
            SpannableString string = new SpannableString(holder.title.getText().toString());
            if (b){
                string.setSpan(new StrikethroughSpan(),0,holder.title.getText().length(),0);
            } else {
                string.removeSpan(new StrikethroughSpan());
            }
            holder.title.setText(string);
        });
//        holder.listSubTask.setHasFixedSize(true);
        holder.listSubTask.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),LinearLayoutManager.VERTICAL,false));
        holder.listSubTask.setAdapter(new SubTaskAdapter());
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class TaskVH extends RecyclerView.ViewHolder {
        CheckBox check;
        TextView title;
        RecyclerView listSubTask;
        public TaskVH(@NonNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.task_check);
            title = itemView.findViewById(R.id.task_title);
            listSubTask = itemView.findViewById(R.id.list_subtask);
        }
    }
}
