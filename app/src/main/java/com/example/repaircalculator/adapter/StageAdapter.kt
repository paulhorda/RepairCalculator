package com.example.repaircalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.NoteElement
import com.example.repaircalculator.data.entity.Stage
import com.example.repaircalculator.databinding.MaterialsItemBinding
import com.example.repaircalculator.databinding.ProjectElementBinding
import com.example.repaircalculator.viewHolder.MaterialViewHolder
import com.example.repaircalculator.viewHolder.StageViewHolder

class StageAdapter : RecyclerView.Adapter<StageViewHolder>() {

    var stages = emptyList<Stage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var stageCallback: ((Stage) -> AlertDialog?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StageViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ProjectElementBinding.inflate(inflate, parent, false)
        return StageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StageViewHolder, position: Int) {
        holder.bind(stages[position], stageCallback)
    }

    override fun getItemCount(): Int {
        return stages.size
    }
}