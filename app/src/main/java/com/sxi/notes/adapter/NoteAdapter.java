package com.sxi.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {
    private final MySqlHelper db;

    public NoteAdapter(Context context) {
        db = new MySqlHelper(context);
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.title.setText(db.getNote(position).getTitle());
        holder.text.setText(db.getNote(position).getText());
        holder.date.setText(new SimpleDateFormat("hh:mm MMM d, yyyy", Locale.US).format(db.getNote(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return db.getNoteSize();
    }

    static class NoteVH extends RecyclerView.ViewHolder {
        TextView title, text, date;

        public NoteVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            text = itemView.findViewById(R.id.note_text);
            date = itemView.findViewById(R.id.note_date);
        }
    }
}
