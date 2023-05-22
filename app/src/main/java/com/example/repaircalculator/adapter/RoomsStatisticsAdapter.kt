package com.example.repaircalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Room
import com.example.repaircalculator.databinding.RoomStatisticsItemBinding
import com.example.repaircalculator.viewHolder.RoomsStatisticsViewHolder

class RoomsStatisticsAdapter : RecyclerView.Adapter<RoomsStatisticsViewHolder>() {

    var mapOfMaterials = listOf<Pair<Room, List<Material>>>()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    var materialsCallback: ((Room) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsStatisticsViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = RoomStatisticsItemBinding.inflate(inflate, parent, false)
        return RoomsStatisticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomsStatisticsViewHolder, position: Int) {
        holder.bind(mapOfMaterials[position], materialsCallback)
    }

    override fun getItemCount(): Int = mapOfMaterials.size
}