package com.example.repaircalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.databinding.CommonStatisticItemBinding
import com.example.repaircalculator.viewHolder.CommonStatisticViewHolder

class CommonStatisticAdapter : RecyclerView.Adapter<CommonStatisticViewHolder>() {

    private var mapOfTransactions = listOf<Pair<Int, String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonStatisticViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = CommonStatisticItemBinding.inflate(inflate, parent, false)
        return CommonStatisticViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonStatisticViewHolder, position: Int) {
        holder.bind(mapOfTransactions[position])
    }

    override fun getItemCount(): Int = mapOfTransactions.size

    fun setData(rooms: Map<Int, String>) {
        mapOfTransactions = rooms.toList()
        notifyDataSetChanged()
    }
}