package com.example.repaircalculator.viewHolder

import android.net.Uri
import android.opengl.Visibility
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.R
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.NoteElement
import com.example.repaircalculator.data.entity.NoteElementType
import com.example.repaircalculator.databinding.NoteElementBinding
import com.example.repaircalculator.databinding.ProjectElementBinding


class NoteElementViewHolder(private val binding: NoteElementBinding) : RecyclerView.ViewHolder(
    binding.root) {
    fun bind(noteElement: NoteElement, noteElementCallback: ((NoteElement) -> Unit?)?) {
        if(noteElement.type == NoteElementType.IMG){
            binding.noteElementImageIv.visibility = View.VISIBLE
            binding.noteElementImageIv.setImageURI(Uri.parse(noteElement.image))
//            binding.noteElementTitleEt.setText(noteElement.content)
            binding.noteElementTitleEt.visibility = View.GONE
        }
        else {
            binding.noteElementTitleEt.visibility = View.VISIBLE
            binding.noteElementImageIv.visibility = View.GONE
            binding.noteElementTitleEt.setText(noteElement.content)
        }
//        binding.dateProjectTv.text = "2016-2022"
//        binding.statusTv.setText(project.status.resource)
//
//        val greyFilter = PorterDuffColorFilter(ContextCompat.getColor(this.itemView.context,
//            project.status.color), PorterDuff.Mode.MULTIPLY)
//        binding.statusTv.background.colorFilter = greyFilter
//
//        binding.root.setOnClickListener { projectCallback?.invoke(project) }
    }
}