package com.example.githubuser.viewmodelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.Injection
import com.example.githubuser.data.repository.FavUserRepository
import com.example.githubuser.viewmodel.DetailUserViewModel


class ViewModelFactoryDetail constructor(
    private val usersRepository: FavUserRepository
):  ViewModelProvider.NewInstanceFactory() {
    private var username: String? = null
    fun setUsername(username: String) {
        this.username = username
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(requireNotNull(username), usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryDetail? = null
        fun getInstance(context: Context): ViewModelFactoryDetail =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactoryDetail(Injection.provideRepository(context))
            }.also { instance = it }
    }
}