package com.example.muzlib.data.db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.muzlib.data.db.dao.AlbumsDAO
import com.example.muzlib.data.db.entity_search.ResultAlbums

@Database(entities = [ResultAlbums::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDAO
}