package com.example.repaircalculator.viewHolder

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moneysaver.utils.format
import com.example.moneysaver.utils.toDate
import com.example.repaircalculator.R
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.Stage
import com.example.repaircalculator.databinding.MaterialsItemBinding
import com.example.repaircalculator.databinding.ProjectElementBinding


class StageViewHolder(private val binding: ProjectElementBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(stage: Stage, stageCallback: ((Stage) -> AlertDialog?)?) {

        binding.projectNameTv.text = stage.title
        binding.dateProjectTv.text = buildString {
            append(stage.dateStart.toDate().format())
            append(" - ")
            append(stage.dateEnd.toDate().format())
        }

        val textId =  if (stage.isFinish) R.string.finished
        else if (stage.dateStart <= System.currentTimeMillis()) R.string.in_progress
        else R.string.not_started

        binding.statusTv.setText(
          textId
        )

        val color = when (textId) {
            R.string.finished -> R.color.finished
            R.string.in_progress -> R.color.in_progress
            else -> R.color.not_started
        }

        val greyFilter = PorterDuffColorFilter(
            ContextCompat.getColor(
                this.itemView.context,
               color
            ), PorterDuff.Mode.MULTIPLY
        )
        binding.statusTv.background.colorFilter = greyFilter


        binding.root.setOnClickListener { stageCallback?.invoke(stage) }
    }
}