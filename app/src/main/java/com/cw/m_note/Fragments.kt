package com.cw.m_note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class NotesFragment : Fragment() {

    private lateinit var notesList: RecyclerView
    private lateinit var fab: FloatingActionButton

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val msg = when (it.resultCode) {
                Database.ADD -> "Added"
                Database.EDIT -> "Edited"
                Database.DELETE -> "Deleted"
                else -> "Canceled"
            }
            Snackbar.make(requireContext(), requireView(), msg, Snackbar.LENGTH_SHORT).show()
            notesList.adapter?.notifyDataSetChanged()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_notes, container, false)
        fab = requireActivity().findViewById(R.id.main_fab)
        notesList = v.findViewById(R.id.notes_list)
        notesList.setHasFixedSize(true)
        notesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        notesList.adapter = NotesAdapter(launcher)
        registerForContextMenu(notesList)

        notesList.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    fab.hide()
                } else {
                    fab.show()
                }
            }
        })

        fab.setOnClickListener {
            launcher.launch(
                Intent(requireContext(), NoteEditorActivity::class.java).putExtra(
                    "mode",
                    Database.ADD
                )
            )
        }
        return v
    }
}

class TasksFragment : Fragment() {

    private lateinit var tasksList: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tasks, container, false)
        tasksList = v.findViewById(R.id.tasks_list)
        tasksList.setHasFixedSize(true)
        tasksList.layoutManager = LinearLayoutManager(context)
        return v
    }
}