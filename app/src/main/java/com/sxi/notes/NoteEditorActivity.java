package com.sxi.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sxi.notes.databinding.ActivityNoteEditorBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteEditorActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding binding;
    private long date;
    private String dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd h:mm", Locale.US);
        date = calendar.getTimeInMillis();
        dc = format.format(calendar.getTime());
        binding.date.setText(dc.concat(" | 0 Character"));

        binding.editorText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.toString().trim()
                        .replace(" ", "")
                        .replace("\n", "").length();
                if (length < 2) {
                    binding.date.setText(String.format(dc.concat(" | %s Character"), length));
                } else {
                    binding.date.setText(String.format(dc.concat(" | %s Characters"), length));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        SharedPreferences sh = getSharedPreferences("font_size",MODE_PRIVATE);
        sh.getString("key",null);
        loadTextSizeFromPreference(sh);


    }

    public void saveToSharedPreferences(){
        SharedPreferences.Editor editor = getSharedPreferences("font_size",MODE_PRIVATE).edit();
        editor.putString("key", String.valueOf(R.string.pref_text_value));
    }
    private void loadTextSizeFromPreference(SharedPreferences sharedPreferences) {
        changeTextSize(sharedPreferences.getString(getString(R.string.pref_text_key),getString(R.string.pref_text_value)));
    }

    private void changeTextSize(String pref_text_value) {
        Log.d("Tsize", pref_text_value);
        if (pref_text_value.equals("Small")) {
            binding.editorText.setTextSize(10);
        } else if(pref_text_value.equals("Medium")) {
            binding.editorText.setTextSize(20);
        } else if(pref_text_value.equals("Large")){
            binding.editorText.setTextSize(30);
        }else{
            binding.editorText.setTextSize(40);
        }
    }


    @Override
    public void onBackPressed() {
        String title = binding.editorTitle.getText().toString(),
                text = binding.editorText.getText().toString();
        if (title.equals("") && text.equals("")) {
            setResult(RESULT_CANCELED);
        } else {
            Intent intent = new Intent()
                    .putExtra("title", title)
                    .putExtra("text", text)
                    .putExtra("date", date)
                    .putExtra("theme", 0);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}