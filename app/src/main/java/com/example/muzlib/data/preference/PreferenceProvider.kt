package com.example.muzlib.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences =
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        preference.edit { putString(KEY_DEFAULT_ENTITY, DEFAULT_ENTITY) }
        preference.edit { putString(KEY_TRACK_ENTITY, TRACK_ENTITY) }
    }

    fun getDefaultEntity(): String {
        return preference.getString(KEY_DEFAULT_ENTITY, DEFAULT_ENTITY) ?: DEFAULT_ENTITY
    }

    fun getTrackEntity(): String {
        return preference.getString(KEY_TRACK_ENTITY, TRACK_ENTITY) ?: TRACK_ENTITY
    }

    companion object {
        private const val KEY_DEFAULT_ENTITY = "default_entity"
        private const val DEFAULT_ENTITY = "album"
        private const val KEY_TRACK_ENTITY = "track_entity"
        private const val TRACK_ENTITY = "song"
    }
}