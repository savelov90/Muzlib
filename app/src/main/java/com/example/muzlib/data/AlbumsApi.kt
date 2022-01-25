package com.example.muzlib.data

import com.example.muzlib.data.db.entity_search.SearchAlbums
import com.example.muzlib.data.db.entity_track.SearchTracks
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val SEARCH = "search"
private const val LOOKUP = "lookup"
private const val TERM = "term"
private const val ENTITY = "entity"
private const val ID = "id"

interface AlbumsApi {
    @GET(SEARCH)
    fun getAlbums(
        @Query(TERM) searchResult: String,
        @Query(ENTITY) album: String
    ): Observable<SearchAlbums>

    @GET(LOOKUP)
    fun getTracks(
        @Query(ID) album: String,
        @Query(ENTITY) track: String
    ): Observable<SearchTracks>
}