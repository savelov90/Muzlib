package com.example.muzlib.di

import com.example.muzlib.di.modules.DatabaseModule
import com.example.muzlib.di.modules.DomainModule
import com.example.muzlib.di.modules.RemoteModule
import com.example.muzlib.viewmodel.DetailsFragmentViewModel
import com.example.muzlib.viewmodel.HomeFragmentViewModel

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)

interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(detailsFragmentViewModel: DetailsFragmentViewModel)
}