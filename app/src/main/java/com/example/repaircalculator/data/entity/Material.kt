package com.example.repaircalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Materials")
data class Material(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "note_id") val noteId: Int,
    @ColumnInfo(name = "table_num") val tableNum: Int,
    var title: String,
    var count: Int,
    var price: Double,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
