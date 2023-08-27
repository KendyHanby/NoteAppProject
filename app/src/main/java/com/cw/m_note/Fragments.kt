package com.cw.m_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotesFragment : Fragment() {

    private lateinit var notesList: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_notes, container, false)
        notesList = v.findViewById(R.id.notes_list)
        notesList.setHasFixedSize(true)
        notesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        notesList.adapter = NotesAdapter()

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