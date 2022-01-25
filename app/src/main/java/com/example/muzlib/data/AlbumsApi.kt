package com.example.muzlib.data

import com.example.muzlib.data.db.entity_search.SearchAlbums
import com.example.muzlib.data.db.entity_track.SearchTracks
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface AlbumsApi {
    @GET("search")
    fun getAlbums(
        @Query("term") searchResult: String,
        @Query("entity") album: String
    ): Observable<SearchAlbums>

    @GET("lookup")
    fun getTracks(
        @Query("id") album: String,
        @Query("entity") track: String
    ): Observable<SearchTracks>
}