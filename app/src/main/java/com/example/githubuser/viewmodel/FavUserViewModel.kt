package com.example.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.entity.FavoriteUser
import com.example.githubuser.data.repository.FavUserRepository

class FavUserViewModel(
    usersRepository: FavUserRepository,
):ViewModel() {
    val listUsers: LiveData<List<FavoriteUser>> = usersRepository.getFavList()

}