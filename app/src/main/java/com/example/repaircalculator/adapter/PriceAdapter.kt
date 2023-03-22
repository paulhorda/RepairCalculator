package com.example.repaircalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.NotePrice
import com.example.repaircalculator.databinding.NotePriceBinding
import com.example.repaircalculator.databinding.PriceElementBinding
import com.example.repaircalculator.databinding.ProjectElementBinding
import com.example.repaircalculator.viewHolder.NoteViewHolder
import com.example.repaircalculator.viewHolder.PriceViewHolder

class PriceAdapter : RecyclerView.Adapter<PriceViewHolder>() {

    var prices = emptyList<NotePrice>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var noteCallback: ((NotePrice) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = PriceElementBinding.inflate(inflate, parent, false)
        return PriceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        holder.bind(prices[position], noteCallback)
    }

    override fun getItemCount(): Int {
        return prices.size
    }
}