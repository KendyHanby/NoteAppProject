package com.sxi.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
        });
        return binding.getRoot();
    }
}