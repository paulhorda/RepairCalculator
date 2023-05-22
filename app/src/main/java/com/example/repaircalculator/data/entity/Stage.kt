package com.example.repaircalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Stages")
data class Stage(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "project_id") val projectId: Int,
    val title: String,
    @ColumnInfo(name = "date_start") val dateStart: Long,
    @ColumnInfo(name = "date_end") val dateEnd: Long,
    @ColumnInfo(name = "actual_date_end") val actualDateEnd: Long?,
    @ColumnInfo(name = "is_finish") val isFinish: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
