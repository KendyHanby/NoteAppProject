package com.sxi.notes.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.model.NoteModel;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {

    private List<NoteModel> list;

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NoteVH extends RecyclerView.ViewHolder {
        public NoteVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
