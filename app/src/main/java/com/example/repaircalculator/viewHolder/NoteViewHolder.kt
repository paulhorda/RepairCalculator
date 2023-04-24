package com.example.repaircalculator.viewHolder

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moneysaver.utils.format
import com.example.moneysaver.utils.toDate
import com.example.repaircalculator.R
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.databinding.ProjectElementBinding


class NoteViewHolder(private val binding: ProjectElementBinding) : RecyclerView.ViewHolder(
    binding.root) {
    fun bind(note: Note, noteCallback: ((Note) -> Unit?)?) {

        binding.projectNameTv.text = note.title.ifEmpty {
            itemView.context.getString(R.string.no_title)
        }

        binding.dateProjectTv.text = note.createdAt.toDate().format()
        binding.statusTv.setText(note.type.resource)

        val greyFilter = PorterDuffColorFilter(ContextCompat.getColor(this.itemView.context,
            note.type.color), PorterDuff.Mode.MULTIPLY)
        binding.statusTv.background.colorFilter = greyFilter

        binding.root.setOnClickListener { noteCallback?.invoke(note) }
    }
}