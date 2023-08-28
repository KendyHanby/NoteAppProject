package com.cw.m_note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import com.cw.m_note.databinding.ActivityNoteEditorBinding

class NoteEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteEditorBinding
    private lateinit var database: Database
    private var isEdit = false
    private lateinit var doneMenu: MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            setResult(-1)
            onBackPressedDispatcher.onBackPressed()
        }

        database = Database(this)
        isEdit = intent.hasExtra("id")
        if (isEdit) {
            val noteModel = database.getNote(-1, intent.getIntExtra("id", 0))
            binding.noteTitle.setText(noteModel.title)
            binding.noteContent.setText(noteModel.content)
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        doneMenu = menu.add("Done").setIcon(R.drawable.round_done_24)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item == doneMenu) {
            setResult(intent.getIntExtra("position", -1))
            if (isEdit) {
                database.editNote(
                    NoteModel(
                        intent.getIntExtra("id", -1),
                        binding.noteTitle.text.toString(),
                        binding.noteContent.text.toString(),
                        System.currentTimeMillis().toDouble()
                    )
                )
            }
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}