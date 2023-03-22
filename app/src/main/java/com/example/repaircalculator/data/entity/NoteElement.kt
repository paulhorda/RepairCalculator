package com.example.repaircalculator.data.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteElements")
data class NoteElement(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "parent_id") val parentId: Int,
    val type: NoteElementType,
    val image: String,
    val content:String,
    @ColumnInfo(name = "child_id") val childId: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
