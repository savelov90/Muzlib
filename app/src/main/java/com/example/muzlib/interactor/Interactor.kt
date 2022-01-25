package com.example.muzlib.interactor


import com.example.muzlib.data.MainRepository
import com.example.muzlib.data.AlbumsApi
import com.example.muzlib.data.db.entity_search.ResultAlbums
import com.example.muzlib.data.db.entity_track.ResultTracks
import com.example.muzlib.data.preference.PreferenceProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


class Interactor(
    private val repo: MainRepository,
    private val retrofitService: AlbumsApi,
    private val preferences: PreferenceProvider
) {
    lateinit var albumsObservable: Observable<List<ResultAlbums>>

    fun getAlbumsFromApi(search: String): Observable<List<ResultAlbums>> {

        albumsObservable = retrofitService.getAlbums(search, getDefaultEntityFromPreferences())
            .subscribeOn(Schedulers.io())
            .map { searchAlbums ->
                val sortedAlbums = searchAlbums.results
                sortedAlbums.sortedBy { it.collectionName }
            }

        albumsObservable.subscribeBy(
            onError = {

            },
            onNext = {
                repo.deleteAll()
                repo.putToDb(it)
            }
        )
        return albumsObservable
    }

    fun getTracksFromApi(albumId: String): Observable<List<String>> = retrofitService
        .getTracks(albumId, getTrackEntityFromPreferences())
        .subscribeOn(Schedulers.io())
        .map {
            it.results
        }
        .map { list ->
            val tracksNameList = mutableListOf<String>()
            list.forEach { tracksNameList.add(it.trackName) }
            tracksNameList
        }

    fun getAlbumsFromDB(): Observable<List<ResultAlbums>> = repo.getAllFromDB()

    //Методы для получения настроек поиска
    private fun getDefaultEntityFromPreferences() = preferences.getDefaultEntity()

    private fun getTrackEntityFromPreferences() = preferences.getTrackEntity()
}