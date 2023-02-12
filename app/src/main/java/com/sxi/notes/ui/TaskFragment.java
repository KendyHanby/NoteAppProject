package com.sxi.notes.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sxi.notes.R;
import com.sxi.notes.adapter.TaskAdapter;
import com.sxi.notes.databinding.FragmentTaskBinding;

public class TaskFragment extends Fragment {

    private FragmentTaskBinding binding;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater,container,false);
        binding.listTask.setHasFixedSize(true);
        binding.listTask.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        binding.listTask.setAdapter(new TaskAdapter());

        FloatingActionButton mainFab = requireActivity().findViewById(R.id.main_fab);
        mainFab.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
            View v = dialog.getLayoutInflater().inflate(R.layout.task_editor,null,true);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            dialog.setContentView(v);
            dialog.show();
        });
        return binding.getRoot();
    }
}