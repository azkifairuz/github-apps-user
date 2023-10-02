package com.example.githubuser.viewmodelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.Injection
import com.example.githubuser.data.repository.FavUserRepository
import com.example.githubuser.viewmodel.FavUserViewModel

class FavUserViewModelFactory(
    private val usersRepository: FavUserRepository
) : ViewModelProvider.NewInstanceFactory()  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavUserViewModel::class.java)) {
            return FavUserViewModel(usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: FavUserViewModelFactory? = null
        fun getInstance(context: Context): FavUserViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavUserViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}