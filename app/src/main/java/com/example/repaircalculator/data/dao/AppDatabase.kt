package com.example.repaircalculator.data.dao

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repaircalculator.data.entity.*

@Keep
@Database(entities = [Project::class, User::class, Room::class, Note::class, NoteElement::class, NotePrice::class, Material::class, Stage::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
    abstract fun noteDao(): NoteDao
    abstract fun noteElementDao(): NoteElementDao
    abstract fun notePriceDao(): NotePriceDao
    abstract fun materialDao(): MaterialDao
    abstract fun stageDao(): StageDao
}