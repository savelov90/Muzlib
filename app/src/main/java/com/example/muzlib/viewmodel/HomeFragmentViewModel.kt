package com.example.muzlib.viewmodel


import androidx.lifecycle.ViewModel
import com.example.muzlib.App
import com.example.muzlib.data.db.entity_search.ResultAlbums
import com.example.muzlib.interactor.Interactor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {
    val albumsListData: Observable<List<ResultAlbums>>

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        albumsListData = interactor.getAlbumsFromDB()
    }

    fun getAlbums(search: String) = interactor.getAlbumsFromApi(search)
}
