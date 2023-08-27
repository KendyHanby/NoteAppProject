package com.cw.m_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment() {

    private lateinit var notesList: RecyclerView
    private lateinit var fab: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_notes, container, false)
        fab = requireActivity().findViewById(R.id.main_fab)
        notesList = v.findViewById(R.id.notes_list)
        notesList.setHasFixedSize(true)
        notesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        notesList.adapter = NotesAdapter()
        notesList.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                log("dx $dx dy $dy")
            }
        })

        fab.setOnClickListener {
            Database(requireContext()).insert(NoteModel(null, "Test 1", "contenndkvk", 123.4))
            notesList.adapter?.notifyDataSetChanged()
        }
        return v
    }
}

class TasksFragment : Fragment() {

    private lateinit var tasksList: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tasks, container, false)
        tasksList = v.findViewById(R.id.tasks_list)
        tasksList.setHasFixedSize(true)
        tasksList.layoutManager = LinearLayoutManager(context)
        return v
    }
}