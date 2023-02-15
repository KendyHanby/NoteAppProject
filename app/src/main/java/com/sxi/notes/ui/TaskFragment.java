package com.sxi.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sxi.notes.R;
import com.sxi.notes.adapter.TaskAdapter;
import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.databinding.FragmentTaskBinding;
import com.sxi.notes.data.model.TaskModel;

public class TaskFragment extends Fragment {

    private FragmentTaskBinding binding;
    private MySqlHelper db;
    private FloatingActionButton fab;
    private TaskEditorFragment editorFragment;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySqlHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater,container,false);

        binding.listTask.setHasFixedSize(true);
        binding.listTask.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        binding.listTask.setAdapter(new TaskAdapter(requireContext()));

        fab = requireActivity().findViewById(R.id.main_fab);
        editorFragment = new TaskEditorFragment();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.setOnClickListener(view -> {
            editorFragment.show(getChildFragmentManager(),null);
            editorFragment.setOnSave((title, reminder, isDone) -> {
                if (db.saveTask(new TaskModel(title,reminder,isDone))==-1) {
                    Toast.makeText(requireContext(), "Can't Save Task", Toast.LENGTH_SHORT).show();
                }
                ((RecyclerView.Adapter<?>)binding.listTask.getAdapter()).notifyDataSetChanged();
            });
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        fab.setOnClickListener(null);
    }
}