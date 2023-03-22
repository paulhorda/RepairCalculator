package com.example.repaircalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Project
import com.example.repaircalculator.data.entity.Room
import com.example.repaircalculator.databinding.ProjectElementBinding
import com.example.repaircalculator.databinding.RoomElementBinding
import com.example.repaircalculator.viewHolder.ProjectViewHolder
import com.example.repaircalculator.viewHolder.RoomsViewHolder

class RoomsAdapter : RecyclerView.Adapter<RoomsViewHolder>() {

    var roomCallback: ((Room) -> Unit?)? = null

    var rooms = emptyList<Room>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = RoomElementBinding.inflate(inflate, parent, false)
        return RoomsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        holder.bind(rooms[position], roomCallback)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}