package com.sxi.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.data.model.Notes;
import com.sxi.notes.databinding.ActivityNoteEditorBinding;
import com.sxi.notes.editor.MySelectionActionModeCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteEditorActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding binding;
    private MySqlHelper db;
    private long date, edit;
    private String dateInText;
    private boolean isEditing;
    private int id;

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

        db = new MySqlHelper(getApplicationContext());

        binding.toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd h:mm", Locale.US);

        isEditing = getIntent().getIntExtra("id", -1)!=-1;

        if (isEditing) {
            id = getIntent().getIntExtra("id",-1);
            binding.editorTitle.setText(db.getNote(id).getTitle());
            binding.editorText.setText(db.getNote(id).getText());
            date = db.getNote(id).getDate();
            dateInText = format.format(db.getNote(id).getEdit());
            binding.date.setText(dateInText.concat(String.format(" | %s Character", binding.editorText.getText().length())));

        } else {
            date = calendar.getTimeInMillis();
            dateInText = format.format(calendar.getTime());
            binding.date.setText(dateInText.concat(" | 0 Character"));
        }
        binding.editorText.addTextChangedListener(new TextWatcher() {
            private int start;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                int length = s.toString().trim()
                        .replace(" ", "")
                        .replace("\n", "").length();
                if (length < 2) {
                    binding.date.setText(String.format(dateInText.concat(" | %s Character"), length));
                } else {
                    binding.date.setText(String.format(dateInText.concat(" | %s Characters"), length));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                s.setSpan(new UnderlineSpan(),start,s.length(),0);

            }
        });

        binding.editorText.setCustomSelectionActionModeCallback(new MySelectionActionModeCallback(this));

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
        } else if (fontSizeKey.equals("Huge")) {
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
        } else if (colorKey.equals("yellow")) {
            binding.editorText.setTextColor(Color.YELLOW);
        } else if (colorKey.equals("gray")) {
            binding.editorText.setTextColor(Color.GRAY);
        } else if (colorKey.equals("white")) {
            binding.editorText.setTextColor(Color.WHITE);
        } else {
            Toast.makeText(this, "Choose the color", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        String title = binding.editorTitle.getText().toString(),
                text = binding.editorText.getText().toString(),
                        folder = "example";

        Notes notes = new Notes(
                title,
                text,
                folder,
                date,
                isEditing?Calendar.getInstance().getTimeInMillis():date,
                0
        );
        if (title.equals("") && text.equals("")) {
            setResult(RESULT_CANCELED);
        } else {
            if (isEditing){
                db.updateNote(id,notes);
            } else {
                db.saveNote(notes);
            }
            setResult(RESULT_OK, new Intent()
                    .putExtra("id",isEditing?id:-1)
            );
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
        switch (item.getTitle().toString()) {
            case "Reminder":{
                // TODO reminder
                break;
            }
            case "Theme":{
                // TODO theme
                break;
            }
            case "Share":{
                startActivity(Intent.createChooser(new Intent().setAction(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT,binding.editorText.getText().toString()),"Share to..."));
                break;
            }
            case "Move to": {
                startActivity(new Intent(NoteEditorActivity.this, FolderActivity.class));
                break;
            }
            case "Delete": {
                if (id != -1) {
                    setResult(RESULT_OK,new Intent().putExtra("id",-2));
                }
                finish();
                break;
            }
        }


        return super.onOptionsItemSelected(item);
    }
}