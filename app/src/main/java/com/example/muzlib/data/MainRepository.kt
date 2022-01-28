package com.example.muzlib.data

import com.example.muzlib.data.db.dao.AlbumsDAO
import com.example.muzlib.data.db.entity_search.ResultAlbums
import io.reactivex.rxjava3.core.Observable

class MainRepository(private val albumsDAO: AlbumsDAO) {

    fun putToDb(resultAlbums: List<ResultAlbums>) {
            albumsDAO.insertAll(resultAlbums)
    }

    fun getAllFromDB(): Observable<List<ResultAlbums>> = albumsDAO.getCachedNews()

    fun deleteAll() = albumsDAO.deleteAll()
}