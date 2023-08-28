package com.cw.m_note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import com.cw.m_note.databinding.ActivityNoteEditorBinding

class NoteEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteEditorBinding
    private lateinit var database: Database
    private lateinit var noteModel: NoteModel
    private var isEdit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = when (intent.getIntExtra("mode", Database.ADD)) {
            Database.ADD -> "Add"
            else -> "Edit"
        }
        supportActionBar?.subtitle =
            DateFormat.format(getString(R.string.note_date_format), System.currentTimeMillis())
        binding.toolbar.setNavigationOnClickListener {
            setResult(-1)
            onBackPressedDispatcher.onBackPressed()
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setResult(Database.CANCEL)
                finish()
            }
        })

        database = Database(this)
        isEdit = intent.hasExtra("id")
        if (isEdit) {
            noteModel = database.getNote(-1, intent.getIntExtra("id", 0))
            supportActionBar?.subtitle =
                DateFormat.format(getString(R.string.note_date_format), noteModel.date)
            binding.noteTitle.setText(noteModel.title)
            binding.noteTitle.setSelection(noteModel.title.length)
            binding.noteContent.setText(noteModel.content)
            binding.noteContent.setSelection(noteModel.content.length)
        } else {
            noteModel = NoteModel(null, "", "", System.currentTimeMillis())
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Delete").setIcon(R.drawable.round_delete_24)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menu.add("Done").setIcon(R.drawable.round_done_24)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Done" -> {
                if (isEdit) {
                    setResult(Database.EDIT)
                    database.editNote(
                        NoteModel(
                            intent.getIntExtra("id", -1),
                            binding.noteTitle.text.toString(),
                            binding.noteContent.text.toString(),
                            System.currentTimeMillis()
                        )
                    )
                } else {
                    setResult(Database.ADD)
                    database.insert(
                        NoteModel(
                            intent.getIntExtra("id", -1),
                            binding.noteTitle.text.toString(),
                            binding.noteContent.text.toString(),
                            System.currentTimeMillis()
                        )
                    )
                }
            }

            "Delete" -> {
                setResult(Database.DELETE)
                database.delete(
                    NoteModel(
                        intent.getIntExtra("id", -1),
                        binding.noteTitle.text.toString(),
                        binding.noteContent.text.toString(),
                        System.currentTimeMillis()
                    )
                )
            }
        }
        onBackPressedDispatcher.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}