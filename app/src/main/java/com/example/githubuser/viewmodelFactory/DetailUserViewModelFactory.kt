package com.example.githubuser.viewmodelFactory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.Injection
import com.example.githubuser.data.repository.FavUserRepository
import com.example.githubuser.viewmodel.DetailUserViewModel


class DetailUserViewModelFactory(
    private val usersRepository: FavUserRepository
) : ViewModelProvider.NewInstanceFactory() {
    private var username: String? = null
    fun setUsername(username: String) {
        this.username = username
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(requireNotNull(username), usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: DetailUserViewModelFactory? = null
        fun getInstance(context: Context): DetailUserViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailUserViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}