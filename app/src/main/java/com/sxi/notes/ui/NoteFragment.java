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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sxi.notes.FolderActivity;
import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.NoteEditorActivity;
import com.sxi.notes.R;
import com.sxi.notes.adapter.NoteAdapter;
import com.sxi.notes.databinding.FragmentNoteBinding;
import com.sxi.notes.data.model.NoteModel;

public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    private MySqlHelper db;
    private FloatingActionButton fab;
    private SearchView searchView;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode()==RESULT_OK){
            ((RecyclerView.Adapter<?>)binding.listNote.getAdapter()).notifyDataSetChanged();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        binding.listNote.setHasFixedSize(true);

        binding.listNote.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.listNote.setAdapter(new NoteAdapter(requireContext(), launcher));

        fab = requireActivity().findViewById(R.id.main_fab);
        searchView = requireActivity().findViewById(R.id.main_search);
        searchView.setOnSearchClickListener(view -> {
            fab.hide();
        });
        searchView.setOnCloseListener(() -> {
            fab.show();
            return false;
        });

        binding.all.setOnClickListener(v -> {
            binding.listNote.scrollToPosition(0);
        });
        binding.folder.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), FolderActivity.class));
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.setOnClickListener(view -> {
            launcher.launch(new Intent(requireContext(), NoteEditorActivity.class)
                    .putExtra("is_edit",false)
            );
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        fab.setOnClickListener(null);
        searchView.setIconified(true);
    }

}