package com.example.repaircalculator.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.moneysaver.utils.format
import com.example.moneysaver.utils.toDate
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.NotePrice
import com.example.repaircalculator.databinding.PriceElementBinding
import com.example.repaircalculator.databinding.ProjectElementBinding


class PriceViewHolder(private val binding: PriceElementBinding) : RecyclerView.ViewHolder(
    binding.root) {
    fun bind(notePrice: NotePrice, noteCallback: ((NotePrice) -> Unit?)?) {
        binding.priceNameTv.text = notePrice.title
        binding.priceTv.text = notePrice.price.toString()
//        binding.statusTv.setText(project.status.resource)
//
//        val greyFilter = PorterDuffColorFilter(ContextCompat.getColor(this.itemView.context,
//            project.status.color), PorterDuff.Mode.MULTIPLY)
//        binding.statusTv.background.colorFilter = greyFilter
//
        binding.root.setOnClickListener { noteCallback?.invoke(notePrice) }
    }
}