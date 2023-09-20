package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.response.SearchUserResponse
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel : ViewModel() {

    private var _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    init {
        searchUser("azki")
    }
    fun onSearchChange(text: String) {
        _searchText.value = text
    }

    fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().findUser(username)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e("isFailed Get User", " ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("onFailure Get User", "onFailure: ${t.message.toString()}")
            }
        })
    }
}