package com.sxi.notes.adapter;

import static com.sxi.notes.Utils.ROTATION;
import static com.sxi.notes.Utils.TRANSLATIONY;
import static com.sxi.notes.Utils.setAnimation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.R;
import com.sxi.notes.Utils;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TVH>{


    @NonNull
    @Override
    public TVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task_item,parent,false));
    }
    boolean isEX = false;
    @Override
    public void onBindViewHolder(@NonNull TVH holder, int position) {

        for (int i = 0; i < position; i++) {
            View layout = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.list_subtask_item,null,false);

            holder.taskSub.addView(layout);
        }

        if (isEX){
            holder.taskSub.setVisibility(View.VISIBLE);
        } else {
            holder.taskSub.setVisibility(View.GONE);
        }
        holder.taskCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            SpannableString string = new SpannableString(holder.taskTitle.getText().toString());
            if (b){
                string.setSpan(new StrikethroughSpan(),0,holder.taskTitle.getText().length(),0);
            } else {
                string.removeSpan(new StrikethroughSpan());
            }
            holder.taskTitle.setText(string);
        });
        holder.taskEx.setOnClickListener(v -> {
            if (isEX){
                setAnimation(holder.taskEx,ROTATION,100,180,360);
                setAnimation(holder.taskSub,TRANSLATIONY,100,0,-50);
                holder.taskSub.setVisibility(View.GONE);
            }else {
                setAnimation(holder.taskEx,ROTATION,100,0,180);
                setAnimation(holder.taskSub,TRANSLATIONY,100,-50,0);
                holder.taskSub.setVisibility(View.VISIBLE);
            }
            isEX = !isEX;
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class TVH extends RecyclerView.ViewHolder {
        CheckBox taskCheck;
        TextView taskTitle;
        ImageView taskEx;
        LinearLayout taskSub;
        public TVH(@NonNull View itemView) {
            super(itemView);
            taskCheck = itemView.findViewById(R.id.task_check);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskEx = itemView.findViewById(R.id.task_ex);
            taskSub = itemView.findViewById(R.id.task_sub);
        }
    }
}
