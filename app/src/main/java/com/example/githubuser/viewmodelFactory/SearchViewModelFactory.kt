package com.example.githubuser.viewmodelFactory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.Injection
import com.example.githubuser.setting.SettingPreference
import com.example.githubuser.viewmodel.SearchUserViewModel

class SearchViewModelFactory private constructor(private val pref: SettingPreference) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) {
            return SearchUserViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SearchViewModelFactory? = null
        fun getInstance(context: Context, dataStore: DataStore<Preferences>): SearchViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SearchViewModelFactory(
                    Injection.provideThemeSetting(dataStore))
            }.also { instance = it }
    }
}