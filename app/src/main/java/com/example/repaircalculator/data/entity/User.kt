package com.example.repaircalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val login:String,
    val password:String,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
