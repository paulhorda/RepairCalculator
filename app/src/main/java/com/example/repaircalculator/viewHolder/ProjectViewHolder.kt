package com.example.repaircalculator.viewHolder

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moneysaver.utils.format
import com.example.moneysaver.utils.toDate
import com.example.repaircalculator.data.entity.Project
import com.example.repaircalculator.databinding.ProjectElementBinding


class ProjectViewHolder(private val binding: ProjectElementBinding) : RecyclerView.ViewHolder(
    binding.root) {
    fun bind(project: Project, projectCallback: ((Project) -> Unit?)?) {
        binding.projectNameTv.text = project.name
        binding.dateProjectTv.text = buildString {
        append(project.dateStart.toDate().format())
        append(" - ")
        append(project.dateEnd.toDate().format())
    }
        binding.statusTv.setText(project.status.resource)

        val greyFilter = PorterDuffColorFilter(ContextCompat.getColor(this.itemView.context,
            project.status.color), PorterDuff.Mode.MULTIPLY)
        binding.statusTv.background.colorFilter = greyFilter

        binding.root.setOnClickListener { projectCallback?.invoke(project) }
    }
}