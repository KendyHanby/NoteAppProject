package com.sxi.notes.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.R;
import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.data.model.Tasks;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TVH> {

    private MySqlHelper db;

    public TaskAdapter(MySqlHelper db) {
        this.db = db;
    }

    @NonNull
    @Override
    public TVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TVH holder, int position) {

        String title = db.getTask(position).getTitle();
        long reminder = db.getTask(position).getReminder();
        boolean isDone = db.getTask(position).getStatus();

        holder.taskTitle.setText(setStrike(title, isDone));
        holder.taskCheck.setChecked(isDone);

        // TODO delete task

        // for check done effect
        holder.taskCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            // save check state to database
            db.updateTask(position, new Tasks(title, reminder, b));
            holder.taskTitle.setText(setStrike(title, b));
        });

        holder.taskDel.setOnClickListener(view -> {
            db.deleteTask(position);
            notifyItemRemoved(position);
        });
    }

    private static SpannableString setStrike(String text, boolean is) {
        SpannableString string = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#949494"));
        int length = text.length();
        if (is) {
            string.setSpan(new StrikethroughSpan(), 0, length, 0);
            string.setSpan(colorSpan,0,length,0);
        } else {
            string.removeSpan(new StrikethroughSpan());
            string.removeSpan(colorSpan);
        }
        return string;
    }


    @Override
    public int getItemCount() {
        return db.getTaskSize();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    static class TVH extends RecyclerView.ViewHolder {
        CheckBox taskCheck;
        TextView taskTitle;
        ImageView taskDel;

        public TVH(@NonNull View itemView) {
            super(itemView);
            taskCheck = itemView.findViewById(R.id.task_check);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDel = itemView.findViewById(R.id.task_del);
        }
    }
}
