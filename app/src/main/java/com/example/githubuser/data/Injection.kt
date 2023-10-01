package com.example.githubuser.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.githubuser.data.db.FavUserDb
import com.example.githubuser.data.repository.FavUserRepository
import com.example.githubuser.setting.SettingPreference


object Injection {
    fun provideRepository(context: Context): FavUserRepository {
        val database = FavUserDb.getInstance(context)
        val dao = database.favUserDao()
        return FavUserRepository.getInstance(favUserDao = dao)
    }

    fun provideThemeSetting(dataStore: DataStore<Preferences>): SettingPreference {
        return SettingPreference.getInstance(dataStore)
    }
}