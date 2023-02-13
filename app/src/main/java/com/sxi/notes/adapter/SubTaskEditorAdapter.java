package com.sxi.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.MySqlHelper;
import com.sxi.notes.R;
import com.sxi.notes.model.SubTaskModel;

import java.util.List;

public class SubTaskEditorAdapter extends RecyclerView.Adapter<SubTaskEditorAdapter.STVH> {

    private List<SubTaskModel> list;
    private MySqlHelper db;

    public SubTaskEditorAdapter(List<SubTaskModel> list, Context context) {
        this.list = list;
        db =new MySqlHelper(context);
    }

    @NonNull
    @Override
    public STVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new STVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_editor,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull STVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class STVH extends RecyclerView.ViewHolder {
        public STVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}