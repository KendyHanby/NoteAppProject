package com.sxi.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.R;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.STVH> {
    @NonNull
    @Override
    public STVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new STVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_subtask_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull STVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class STVH extends RecyclerView.ViewHolder {
        public STVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
