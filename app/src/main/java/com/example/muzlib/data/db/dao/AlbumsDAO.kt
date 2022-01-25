package com.example.muzlib.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.muzlib.data.db.entity_search.ResultAlbums
import io.reactivex.rxjava3.core.Observable

@Dao
interface AlbumsDAO {
    @Query("SELECT * FROM search_albums")
    fun getCachedNews(): Observable<List<ResultAlbums>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ResultAlbums>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(resultAlbums: ResultAlbums)

    @Query("DELETE FROM search_albums")
    fun deleteAll()
}