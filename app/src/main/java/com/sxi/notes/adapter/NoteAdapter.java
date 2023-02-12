package com.sxi.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxi.notes.MySqlHelper;
import com.sxi.notes.R;
import com.sxi.notes.data.NoteList;
import com.sxi.notes.data.model.NoteModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {

    private NoteList list;
    private Context context;
    private MySqlHelper db;

    public NoteAdapter(Context context) {
        this.context = context;
        list = new MySqlHelper(context).getNotes();
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
        /*
        holder.title.setText(list.get(position).getTitle());
        holder.text.setText(list.get(position).getText());
        holder.date.setText(new SimpleDateFormat("hh:mm MMM d yyyy", Locale.US).format(new Date(list.get(position).getDate())));
    */
    }

    @Override
    public int getItemCount() {
        return db.getNoteSize();
    }

    class NoteVH extends RecyclerView.ViewHolder {
        TextView title, text, date;

        public NoteVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            text = itemView.findViewById(R.id.note_text);
            date = itemView.findViewById(R.id.note_date);
        }
    }
}
