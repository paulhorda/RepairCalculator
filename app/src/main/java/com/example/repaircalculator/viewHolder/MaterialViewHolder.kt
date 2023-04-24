package com.example.repaircalculator.viewHolder

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.databinding.MaterialsItemBinding


class MaterialViewHolder(private val binding: MaterialsItemBinding) : RecyclerView.ViewHolder(
    binding.root) {
    fun bind(material: Material, noteCallback: ((Material) -> AlertDialog?)?) {

        binding.titleTv.text = material.title
        binding.countTv.text = material.count.toString()
        binding.priceTv.text = material.price.toString()
        binding.fullPriceTv.text = (material.price * material.count).toString()
//        binding.projectNameTv.text = material.title.ifEmpty {
//            itemView.context.getString(R.string.no_title)
//        }
//
//        binding.dateProjectTv.text = material.createdAt.toDate().format()
//        binding.statusTv.setText(project.status.resource)
//
//        val greyFilter = PorterDuffColorFilter(ContextCompat.getColor(this.itemView.context,
//            project.status.color), PorterDuff.Mode.MULTIPLY)
//        binding.statusTv.background.colorFilter = greyFilter
//
        binding.root.setOnClickListener { noteCallback?.invoke(material) }
    }
}