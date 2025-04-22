package com.cw.m_note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.cw.m_note.databinding.ActivityTerBinding
import io.github.rosemoe.sora.langs.java.JavaLanguage

class TerActivity : AppCompatActivity() {
    private lateinit var bind: ActivityTerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTerBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.editor.setEditorLanguage(JavaLanguage())
        bind.terText.setOnClickListener {
            Toast.makeText(applicationContext, "Build from termux", Toast.LENGTH_LONG).show()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        bind.editor?.release()
    }
}