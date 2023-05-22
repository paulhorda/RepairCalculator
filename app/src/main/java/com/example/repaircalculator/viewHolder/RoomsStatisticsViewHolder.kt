package com.example.repaircalculator.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.R
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.Room
import com.example.repaircalculator.databinding.RoomStatisticsItemBinding

class RoomsStatisticsViewHolder(val binding: RoomStatisticsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        materials: Pair<Room, List<Material>>,
        materialsCallback: ((Room) -> Unit?)?
    ) {
        with(binding) {
            categoryItemTitleTv.text = materials.first.name
            val sumOfMaterials = materials.second.sumOf { it.count * it.price }
            categoryItemValueTv.text = itemView.context.getString(
                R.string.uah_amount_placeholder,
                sumOfMaterials.toString()
            )

            root.setOnClickListener { materialsCallback?.invoke(materials.first) }
        }
    }
}