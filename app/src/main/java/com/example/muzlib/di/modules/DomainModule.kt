package com.example.muzlib.di.modules

import android.content.Context
import com.example.muzlib.data.MainRepository
import com.example.muzlib.data.AlbumsApi
import com.example.muzlib.interactor.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule() {

    @Provides
    @Singleton
    fun provideInteractor(
        repository: MainRepository,
        albumsApi: AlbumsApi,
    ) =
        Interactor(
            repo = repository,
            retrofitService = albumsApi,
        )
}