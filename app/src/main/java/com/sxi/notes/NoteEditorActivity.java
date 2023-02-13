package com.sxi.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.sxi.notes.databinding.ActivityNoteEditorBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteEditorActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding binding;
    private long date;
    private String dc;

    @SuppressLint("ResourceAsColor")
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

        //Font Size From Setting
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String font_key = getString(R.string.pref_text_key);
        String fontSizeKey = sharedPreferences.getString(font_key, "Medium");

        if (fontSizeKey.equals("Small")) {
            binding.editorText.setTextSize(14);
        } else if (fontSizeKey.equals("Medium")) {
            binding.editorText.setTextSize(18);
        } else if (fontSizeKey.equals("Large")) {
            binding.editorText.setTextSize(22);
        }else if (fontSizeKey.equals("Huge")) {
            binding.editorText.setTextSize(26);
        } else {
            Toast.makeText(this, "Choose the size", Toast.LENGTH_SHORT).show();
        }

        //Text Color From Setting
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        String color_key = getString(R.string.pref_text_color_key);
        String colorKey = sh.getString(color_key, "blue");
        if (colorKey.equals("red")) {
            binding.editorText.setTextColor(Color.RED);
        } else if (colorKey.equals("green")) {
            binding.editorText.setTextColor(Color.GREEN);
        } else if (colorKey.equals("blue")) {
            binding.editorText.setTextColor(Color.BLUE);
        } else if (colorKey.equals("black")) {
            binding.editorText.setTextColor(Color.BLACK);
        }else if (colorKey.equals("yellow")) {
            binding.editorText.setTextColor(Color.YELLOW);
        }else if (colorKey.equals("gray")) {
            binding.editorText.setTextColor(Color.GRAY);
        }else if (colorKey.equals("white")) {
            binding.editorText.setTextColor(Color.WHITE);
        }
        else {
            Toast.makeText(this, "Choose the color", Toast.LENGTH_SHORT).show();
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