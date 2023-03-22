package com.example.repaircalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.NoteElement
import com.example.repaircalculator.databinding.NoteElementBinding
import com.example.repaircalculator.databinding.ProjectElementBinding
import com.example.repaircalculator.viewHolder.NoteElementViewHolder
import com.example.repaircalculator.viewHolder.NoteViewHolder

class NoteElementAdapter : RecyclerView.Adapter<NoteElementViewHolder>() {

    var notes = emptyList<NoteElement>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var noteCallback: ((NoteElement) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteElementViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = NoteElementBinding.inflate(inflate, parent, false)
        return NoteElementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteElementViewHolder, position: Int) {
        holder.bind(notes[position], noteCallback)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}