package com.example.repaircalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "room_id") val roomId: Int,
    var title: String,
//    val description: String,
    @ColumnInfo(name = "first_note_id") val firstNoteId: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
