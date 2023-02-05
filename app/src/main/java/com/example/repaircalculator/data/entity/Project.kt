package com.example.repaircalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Projects")
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name:String,
    @ColumnInfo(name = "date_start") val dateStart: Long,
    @ColumnInfo(name = "date_end") val dateEnd: Long,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
