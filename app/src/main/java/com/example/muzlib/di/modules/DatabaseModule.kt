package com.example.muzlib.di.modules

import android.content.Context
import androidx.room.Room
import com.example.muzlib.data.MainRepository
import com.example.muzlib.data.db.dao.AlbumsDAO
import com.example.muzlib.data.db.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideNewsDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "albums_db"
        ).build().albumsDao()

    @Provides
    @Singleton
    fun provideRepository(albumsDAO: AlbumsDAO) = MainRepository(albumsDAO)
}