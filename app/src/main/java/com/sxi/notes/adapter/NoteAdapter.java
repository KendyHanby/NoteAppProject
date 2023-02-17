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
import com.sxi.notes.data.model.NoteModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {
    private final MySqlHelper db;
    private ActivityResultLauncher<Intent> launcher;
    private List<NoteModel> list;

    public NoteAdapter(Context context) {
        db = new MySqlHelper(context);
    }

    public NoteAdapter(MySqlHelper db, ActivityResultLauncher<Intent> launcher) {
        this.db = db;
        this.launcher = launcher;
        list = null;
    }

    public NoteAdapter(MySqlHelper db, String query, ActivityResultLauncher<Intent> launcher) {
        this.db = db;
        this.list = db.filterNoteSize(query);
        this.launcher = launcher;
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        String title = list == null ? db.getNote(position).getTitle() : list.get(position).getTitle(),
        text = list == null ? db.getNote(position).getText() : list.get(position).getText();
        long date = list == null ? db.getNote(position).getDate() : list.get(position).getDate(),
                edit = list == null ? db.getNote(position).getEdit() : list.get(position).getEdit();
        int theme = list == null ? db.getNote(position).getTheme() : list.get(position).getTheme();

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
            View root = LayoutInflater.from(dialog.getContext()).inflate(R.layout.item_options_layout, null);
            MaterialButton delete = root.findViewById(R.id.item_del);
            MaterialButton move = root.findViewById(R.id.item_mov);
            delete.setOnClickListener(v -> {
                if (list != null){
                    list.remove(position);
                }
                db.deleteNoteByDate(date);
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
        holder.title.setText(title);
        holder.text.setText(text);
        holder.date.setText(new SimpleDateFormat("HH:mm MMM d, yyyy", Locale.US).format(date));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
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
