package com.sxi.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sxi.notes.databinding.ActivityNoteEditorBinding;

public class NoteEditorActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        if (binding.editorTitle.getText().toString().equals("")||binding.editorText.getText().toString().equals("")){
            setResult(RESULT_CANCELED);
        } else {
            setResult(RESULT_OK);
        }
        super.onDestroy();
    }
}