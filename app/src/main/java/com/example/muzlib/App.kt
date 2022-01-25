package com.example.muzlib

import android.app.Application

import com.example.muzlib.di.AppComponent
import com.example.muzlib.di.DaggerAppComponent
import com.example.muzlib.di.modules.DatabaseModule
import com.example.muzlib.di.modules.DomainModule
import com.example.muzlib.di.modules.RemoteModule


class App : Application() {

    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}