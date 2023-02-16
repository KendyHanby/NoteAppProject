package com.sxi.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sxi.notes.databinding.ActivityFolderBinding;

public class FolderActivity extends AppCompatActivity {
            ActivityFolderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFolderBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(R.layout.activity_folder);

        binding.cv.setOnClickListener(view -> {
            //for new folder click
        });
    }
}