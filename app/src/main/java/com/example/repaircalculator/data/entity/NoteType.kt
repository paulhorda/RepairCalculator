package com.example.repaircalculator.data.entity

import com.example.repaircalculator.R

enum class NoteType(val resource: Int, val color: Int) {
    NOTE(R.string.note, R.color.in_progress),
    TABLE(R.string.table, R.color.freeze)
}