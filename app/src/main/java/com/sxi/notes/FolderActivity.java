package com.sxi.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sxi.notes.databinding.ActivityMoveToFolderBinding;

public class FolderActivity extends AppCompatActivity {
            ActivityMoveToFolderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoveToFolderBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(R.layout.activity_folder);

        binding.cv.setOnClickListener(view -> {
            //for new folder click
        });
    }
}