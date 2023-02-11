package com.sxi.notes.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxi.notes.R;
import com.sxi.notes.adapter.NoteAdapter;
import com.sxi.notes.databinding.FragmentNoteBinding;

public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;

    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater,container,false);
        binding.listNote.setHasFixedSize(true);
        binding.listNote.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        binding.listNote.setAdapter(new NoteAdapter(requireContext()));
        return binding.getRoot();
    }
}