package com.example.muzlib.data

import com.example.muzlib.data.db.entity_search.ResultAlbums
import com.example.muzlib.data.db.dao.AlbumsDAO
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class MainRepository(private val albumsDAO: AlbumsDAO) {

    fun putToDb(resultAlbums: List<ResultAlbums>) {
        Executors.newSingleThreadExecutor().execute {
            albumsDAO.insertAll(resultAlbums)
        }
    }

    fun getAllFromDB(): Observable<List<ResultAlbums>> = albumsDAO.getCachedNews()

    fun deleteAll() = albumsDAO.deleteAll()
}