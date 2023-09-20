package com.example.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.ItemsItem

class SearchUserViewModel:ViewModel() {
    
    private var _searchText = MutableLiveData<String>()
    val searchText:LiveData<String> = _searchText

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private var _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser:LiveData<List<ItemsItem>> = _listUser

    fun onSearchChange(text:String){
        _searchText.value = text
    }

}