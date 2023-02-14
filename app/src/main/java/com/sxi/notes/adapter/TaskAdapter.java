package com.sxi.notes.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.MySqlHelper;
import com.sxi.notes.R;
import com.sxi.notes.model.TaskModel;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TVH>{

    private final MySqlHelper db;
    private final Context context;

    public TaskAdapter(Context context) {
        db = new MySqlHelper(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TVH holder, int position) {

        // TODO here
        holder.taskTitle.setText(db.getTask(position).getTitle());
        holder.taskCheck.setChecked(db.getTask(position).isDone());

        // for check done effect
        holder.taskCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            // save check state to database
            TaskModel model = db.getTask(position);
            if (db.saveTask(new TaskModel(model.getTitle(), model.getReminder(), b))==-1) {
                Toast.makeText(context, "Have a problem!", Toast.LENGTH_SHORT).show();
            }

            // checked strike-through effect
            SpannableString string = new SpannableString(holder.taskTitle.getText().toString());
            if (b){
                string.setSpan(new StrikethroughSpan(),0,holder.taskTitle.getText().length(),0);
            } else {
                string.removeSpan(new StrikethroughSpan());
            }
            holder.taskTitle.setText(string);
        });
    }

    @Override
    public int getItemCount() {
        return db.getTaskSize();
    }

    static class TVH extends RecyclerView.ViewHolder {
        CheckBox taskCheck;
        TextView taskTitle;
        public TVH(@NonNull View itemView) {
            super(itemView);
            taskCheck = itemView.findViewById(R.id.task_check);
            taskTitle = itemView.findViewById(R.id.task_title);
        }
    }
}
