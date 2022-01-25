package com.example.muzlib.viewmodel

import androidx.lifecycle.ViewModel
import com.example.muzlib.App
import com.example.muzlib.data.db.entity_search.ResultAlbums
import com.example.muzlib.data.db.entity_track.ResultTracks
import com.example.muzlib.interactor.Interactor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class DetailsFragmentViewModel : ViewModel() {

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
    }

    fun getTracks(albumId: String): Observable<List<String>> = interactor.getTracksFromApi(albumId)
}
