package com.example.repaircalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rooms")
data class Room(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "project_id") val projectId: Int,
    val name: String,
    val height: Double,
    val square: Double,
    var image: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)
