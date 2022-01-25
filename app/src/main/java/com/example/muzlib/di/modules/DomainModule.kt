package com.example.muzlib.di.modules

import android.content.Context
import com.example.muzlib.data.MainRepository
import com.example.muzlib.data.AlbumsApi
import com.example.muzlib.data.preference.PreferenceProvider
import com.example.muzlib.interactor.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Provides
    @Singleton
    fun provideInteractor(
        repository: MainRepository,
        albumsApi: AlbumsApi,
        preferenceProvider: PreferenceProvider
    ) =
        Interactor(
            repo = repository,
            retrofitService = albumsApi,
            preferences = preferenceProvider
        )
}