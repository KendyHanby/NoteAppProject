package com.cw.m_note

import android.content.Intent
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return NotesFragment()
        } else {
            return TasksFragment()
        }
    }

}

class NotesAdapter(private val launcher: ActivityResultLauncher<Intent>) :
    RecyclerView.Adapter<NotesAdapter.NotesVH>() {

    private lateinit var database: Database

    class NotesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_item_title)
        val content: TextView = itemView.findViewById(R.id.note_item_content)
        val date: TextView = itemView.findViewById(R.id.note_item_date)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        database = Database(recyclerView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVH {
        return NotesVH(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return database.noteSize()
    }

    override fun onBindViewHolder(holder: NotesVH, position: Int) {
        val noteModel = database.getNote(position)
        holder.title.text = noteModel.title
        holder.content.text = noteModel.content
        holder.date.text = DateFormat.format(
            holder.itemView.context.getString(R.string.note_date_format),
            noteModel.date
        )
        holder.itemView.setOnClickListener {
            launcher.launch(
                Intent(
                    holder.itemView.context,
                    NoteEditorActivity::class.java
                )
                    .putExtra("mode", Database.EDIT)
                    .putExtra("id", noteModel.id)
            )
            /*data.remove(noteModel)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, data.size)*/
        }
    }
}

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TasksVH>() {
    class TasksVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksVH {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TasksVH, position: Int) {
        TODO("Not yet implemented")
    }


}