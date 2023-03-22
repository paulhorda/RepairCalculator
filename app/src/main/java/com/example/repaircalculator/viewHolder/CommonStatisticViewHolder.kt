package com.example.repaircalculator.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.databinding.CommonStatisticItemBinding

class CommonStatisticViewHolder(val binding: CommonStatisticItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(statistic: Pair<Int, String>) {
        with(binding) {
            titleTv.setText(statistic.first)
            valueTv.text = statistic.second
        }
    }
}