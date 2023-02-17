package com.sxi.notes.adapter;

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

import com.sxi.notes.R;
import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.data.model.TaskModel;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TVH> {

    private final MySqlHelper db;
    private final String query;
    private List<TaskModel> list;

    public TaskAdapter(MySqlHelper db){
        this.db = db;
        query = null;
        list = null;
    }

    public TaskAdapter(MySqlHelper db,String query){
        this.db = db;
        this.query = query;
        list = db.getFilterTasks(query);
    }

    @NonNull
    @Override
    public TVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TVH holder, int position) {

        String title = query==null?db.getTask(position).getTitle():list.get(position).getTitle();
        long reminder = query==null?db.getTask(position).getReminder():list.get(position).getReminder();
        boolean isDone = query==null?db.getTask(position).getStatus():list.get(position).getStatus();

        setStrike(holder.taskTitle, title , isDone);

        holder.taskCheck.setChecked(isDone);

        // for check done effect
        holder.taskCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            // save check state to database
            TaskModel model = db.getTask(position);
            if (db.updateTask(position, new TaskModel(title, reminder, b)) == -1) {
                Toast.makeText(holder.itemView.getContext(), "Have a problem!", Toast.LENGTH_SHORT).show();
            } else if (query!=null){
                list.set(position,new TaskModel(title,reminder,b));
            }

            // checked strike-through effect
            SpannableString string = new SpannableString(title);
            if (b) {
                string.setSpan(new StrikethroughSpan(), 0, title.length(), 0);
            } else {
                string.removeSpan(new StrikethroughSpan());
            }
            holder.taskTitle.setText(string);
        });
    }

    private static void setStrike(TextView textView, String text, boolean is) {
        SpannableString string = new SpannableString(text);
        if (is) {
            string.setSpan(new StrikethroughSpan(), 0, text.length(), 0);
        } else {
            string.removeSpan(new StrikethroughSpan());
        }
        textView.setText(string);
    }

    @Override
    public int getItemCount() {
        if (query!=null){
            return list.size();
        }
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
