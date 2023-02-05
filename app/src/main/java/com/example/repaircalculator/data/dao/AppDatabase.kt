package com.example.repaircalculator.data.dao

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repaircalculator.data.entity.Project

@Keep
@Database(entities = [Project::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}