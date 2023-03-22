package com.example.repaircalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NotePrices")
data class NotePrice(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "note_id") val noteId: Int,
    var title: String,
    val price: Double,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
