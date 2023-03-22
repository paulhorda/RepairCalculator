package com.example.repaircalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.databinding.ProjectElementBinding
import com.example.repaircalculator.viewHolder.NoteViewHolder

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    var notes = emptyList<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var noteCallback: ((Note) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ProjectElementBinding.inflate(inflate, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], noteCallback)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}