package com.example.githubuser.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.repository.FavUserRepository
import com.example.githubuser.viewmodel.DetailUserViewModel


class DetailUserViewModelFactory(
    private val username: String,
    private val usersRepository: FavUserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailUserViewModel(username, usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}