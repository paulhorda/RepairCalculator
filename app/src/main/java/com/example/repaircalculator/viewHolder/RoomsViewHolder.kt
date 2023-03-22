package com.example.repaircalculator.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Project
import com.example.repaircalculator.data.entity.Room
import com.example.repaircalculator.databinding.ProjectElementBinding
import com.example.repaircalculator.databinding.RoomElementBinding

class RoomsViewHolder(private val binding: RoomElementBinding) : RecyclerView.ViewHolder(
    binding.root) {
    fun bind(room: Room, roomCallback: ((Room) -> Unit?)?) {
        binding.roomTitleTv.text = room.name
        binding.roomSecondTitleTv.text = buildString {
        append("S = ")
        append(room.square)
        append(", h = ")
        append(room.height)
    }

        binding.root.setOnClickListener { roomCallback?.invoke(room) }
    }
}