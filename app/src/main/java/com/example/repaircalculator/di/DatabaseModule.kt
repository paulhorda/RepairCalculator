package com.example.repaircalculator.di

import android.content.Context
import androidx.room.Room
import com.example.repaircalculator.data.dao.*
import com.example.repaircalculator.data.entity.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): ProjectDao {
        return appDatabase.projectDao()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideRoomDao(appDatabase: AppDatabase): RoomDao {
        return appDatabase.roomDao()
    }

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao()
    }

    @Provides
    fun provideNoteElementDao(appDatabase: AppDatabase): NoteElementDao {
        return appDatabase.noteElementDao()
    }

    @Provides
    fun provideNotePriceDao(appDatabase: AppDatabase): NotePriceDao {
        return appDatabase.notePriceDao()
    }

    @Provides
    fun provideMaterialDao(appDatabase: AppDatabase): MaterialDao {
        return appDatabase.materialDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RepairCalculator"
        ).build()
    }
}