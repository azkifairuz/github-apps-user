package com.example.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.entity.FavoriteUser
import com.example.githubuser.data.repository.FavUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavUserViewModel(
    private val usersRepository: FavUserRepository,
):ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val favListUser:LiveData<List<FavoriteUser>> = usersRepository.getFavList()
    fun deleteFav(username: String?) {
        viewModelScope.launch {
            usersRepository.deleteFav(username)
        }
    }
}