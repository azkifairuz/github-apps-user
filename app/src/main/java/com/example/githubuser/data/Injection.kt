package com.example.githubuser.data

import android.content.Context
import com.example.githubuser.data.db.FavUserDb
import com.example.githubuser.data.repository.FavUserRepository
import java.util.prefs.Preferences

object Injection {
    fun provideRepository(context: Context): FavUserRepository {
        val database = FavUserDb.getInstance(context)
        val dao = database.favUserDao()
        return FavUserRepository.getInstance(favUserDao = dao)
    }

//    fun provideThemeSetting(dataStore: DataStore<Preferences>): SettingPreferences {
//        return SettingPreferences.getInstance(dataStore)
//    }
}