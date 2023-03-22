package com.example.repaircalculator.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.viewHolder.ProjectViewHolder
import com.example.repaircalculator.data.entity.Project
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.repaircalculator.databinding.ProjectElementBinding

class ProjectAdapter : RecyclerView.Adapter<ProjectViewHolder>() {

    private var projects = emptyList<Project>()
    var categoryCallback: ((Project) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ProjectElementBinding.inflate(inflate, parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(projects[position], categoryCallback)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    fun setProjects(projects: List<Project>) {
        this.projects = projects
        notifyDataSetChanged()
    }
}