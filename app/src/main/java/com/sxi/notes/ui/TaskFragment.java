package com.sxi.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sxi.notes.R;
import com.sxi.notes.adapter.TaskAdapter;
import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.databinding.FragmentTaskBinding;

public class TaskFragment extends Fragment {

    private FragmentTaskBinding binding;
    private MySqlHelper db;

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
        binding.listTask.setAdapter(new TaskAdapter(db));

        FloatingActionButton fab = requireActivity().findViewById(R.id.main_fab);
        fab.setOnClickListener(view -> {
            TaskEditorFragment editorFragment = new TaskEditorFragment();
            editorFragment.show(getChildFragmentManager(),null);
            editorFragment.setOnSave((title, reminder, isDone) -> {
                ((RecyclerView.Adapter<?>)binding.listTask.getAdapter()).notifyDataSetChanged();
            });
        });
        SearchView searchView = requireActivity().findViewById(R.id.main_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    binding.listTask.setAdapter(new TaskAdapter(db));
                } else {

                }
                return true;
            }
        });
        return binding.getRoot();
    }
}