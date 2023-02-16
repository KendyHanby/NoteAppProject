package com.sxi.notes.adapter;

import android.content.Context;
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
import com.sxi.notes.NoteEditorActivity;
import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {
    private final MySqlHelper db;
    private ActivityResultLauncher<Intent> launcher;

    public NoteAdapter(Context context) {
        db = new MySqlHelper(context);
    }

    public NoteAdapter(Context context, ActivityResultLauncher<Intent> launcher) {
        db = new MySqlHelper(context);
        this.launcher = launcher;
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            launcher.launch(new Intent(v.getContext(), NoteEditorActivity.class)
                    .putExtra("is_edit", true)
                    .putExtra("id", position)
                    .putExtra("title", db.getNote(position).getTitle())
                    .putExtra("text", db.getNote(position).getText())
                    .putExtra("date", db.getNote(position).getDate())
                    .putExtra("edit", db.getNote(position).getEdit())
                    .putExtra("theme", db.getNote(position).getTheme())
            );
        });
        holder.itemView.setOnLongClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(holder.itemView.getContext());
            View root = LayoutInflater.from(dialog.getContext()).inflate(R.layout.item_options_layout, null);
            MaterialButton delete = root.findViewById(R.id.item_del);
            MaterialButton move = root.findViewById(R.id.item_mov);
            delete.setOnClickListener(v -> {
                db.deleteNoteByDate(db.getNote(position).getDate());
                notifyItemRemoved(position);
                dialog.dismiss();
            });
            move.setOnClickListener(v -> {

                dialog.dismiss();
            });
            dialog.setContentView(root);
            dialog.show();
            return true;
        });
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
