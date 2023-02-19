package com.sxi.notes.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.sxi.notes.FolderActivity;
import com.sxi.notes.NoteEditorActivity;
import com.sxi.notes.R;
import com.sxi.notes.data.MySqlHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {
    private final ActivityResultLauncher<Intent> launcher;
    private final MySqlHelper db;

    public NoteAdapter( MySqlHelper db, ActivityResultLauncher<Intent> launcher){
        this.db = db;
        this.launcher = launcher;
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note_item, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        String title = db.getNote(position).getTitle(),
        text = db.getNote(position).getText();
        long date = db.getNote(position).getDate(),
                edit = db.getNote(position).getEdit();
        int theme = db.getNote(position).getTheme();
        holder.itemView.setOnClickListener(v -> {
            launcher.launch(new Intent(v.getContext(), NoteEditorActivity.class)
                    .putExtra("id", position)
                    .putExtra("title", title)
                    .putExtra("text", text)
                    .putExtra("date", date)
                    .putExtra("edit", edit)
                    .putExtra("theme", theme)
            );
            notifyItemChanged(position);
        });
        holder.itemView.setOnLongClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(holder.itemView.getContext());
            @SuppressLint("InflateParams") View root = LayoutInflater.from(dialog.getContext()).inflate(R.layout.item_options_layout, null);
            MaterialButton delete = root.findViewById(R.id.item_del);
            MaterialButton move = root.findViewById(R.id.item_mov);
            delete.setOnClickListener(v -> {
                db.deleteNote(position);
                notifyDataSetChanged();
                dialog.dismiss();
            });
            move.setOnClickListener(v -> {
                dialog.getContext().startActivity(new Intent(dialog.getContext(), FolderActivity.class));
                dialog.dismiss();
            });
            dialog.setContentView(root);
            dialog.show();
            return true;
        });
        holder.title.setText(title);
        holder.text.setText(text);
        holder.date.setText(new SimpleDateFormat("HH:mm MMM d, yyyy", Locale.US).format(date));
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
