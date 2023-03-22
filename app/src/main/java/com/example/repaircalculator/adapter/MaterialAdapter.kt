package com.example.repaircalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.databinding.MaterialsItemBinding
import com.example.repaircalculator.databinding.ProjectElementBinding
import com.example.repaircalculator.viewHolder.MaterialViewHolder
import com.example.repaircalculator.viewHolder.NoteViewHolder

class MaterialAdapter : RecyclerView.Adapter<MaterialViewHolder>() {

    var materials = emptyList<Material>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var noteCallback: ((Note) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = MaterialsItemBinding.inflate(inflate, parent, false)
        return MaterialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaterialViewHolder, position: Int) {
        holder.bind(materials[position], noteCallback)
    }

    override fun getItemCount(): Int {
        return materials.size
    }
}