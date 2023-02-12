package com.sxi.notes.ui;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sxi.notes.MySqlHelper;
import com.sxi.notes.NoteEditorActivity;
import com.sxi.notes.R;
import com.sxi.notes.adapter.NoteAdapter;
import com.sxi.notes.databinding.FragmentNoteBinding;
import com.sxi.notes.model.NoteModel;

public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    private MySqlHelper db;
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Bundle bundle = result.getData().getExtras();
            String title = bundle.getString("title"), text = bundle.getString("text");
            long date = bundle.getLong("date");
            int theme = bundle.getInt("theme");
            if (db.saveNote(new NoteModel(title, text, date, theme)) != -1) {
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show();
                ((RecyclerView.Adapter<?>)binding.listNote.getAdapter()).notifyDataSetChanged();
            }
        } else if (result.getResultCode() == RESULT_CANCELED) {
            Toast.makeText(requireContext(), "Cancel note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Others Error", Toast.LENGTH_SHORT).show();
        }
    });


    public NoteFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySqlHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        binding.listNote.setHasFixedSize(true);
        binding.listNote.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.listNote.setAdapter(new NoteAdapter(requireContext()));

        FloatingActionButton mainFab = requireActivity().findViewById(R.id.main_fab);
        mainFab.setOnClickListener(view -> {
            launcher.launch(new Intent(requireContext(), NoteEditorActivity.class));
        });
        return binding.getRoot();
    }
}