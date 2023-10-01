package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.entity.FavoriteUser
import com.example.githubuser.data.repository.FavUserRepository
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(
    private val username: String,
    private val usersRepository: FavUserRepository,
):ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isLoadingFollow = MutableLiveData<Boolean>()
    val isLoadingFollow: LiveData<Boolean> = _isLoadingFollow
    
    private val _followerList = MutableLiveData<List<ItemsItem>>()
    val followerList: LiveData<List<ItemsItem>> = _followerList
    
    private val _followingList = MutableLiveData<List<ItemsItem>>()
    val followingList: LiveData<List<ItemsItem>> = _followingList
    
    private var _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite
    fun getDetailuser(username:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailUser.value = response.body()
                }else{

                    Log.e("isFailed Get User", " ${response.body()}")
                }

            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("isFailed Get User", " ${t.message.toString()}")
            }

        })
    }

    fun getFollowersList(username: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowersList(username)
        client.enqueue(object :Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollow.value = false
                if (response.isSuccessful){
                    _followerList.value = response.body()
                }else{

                    Log.e("isFailed Get User", " ${response.body()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e("isFailed Get User", " ${t.message.toString()}")
            }

        })
    }
    fun getFollowingList(username: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowingList(username)
        client.enqueue(object :Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollow.value = false
                if (response.isSuccessful){
                    _followingList.value = response.body()
                }else{
                    Log.e("isFailed Get User", " ${response.body()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e("isFailed Get User", " ${t.message.toString()}")
            }
        })
    }

    fun isFav() {
        viewModelScope.launch {
            _isFavorite.value = usersRepository.isFav(username)
        }
    }

    fun addFav(user: FavoriteUser) {
        viewModelScope.launch {
            usersRepository.addFav(user)
        }
    }
    fun deleteFav(username: String?) {
        viewModelScope.launch {
            usersRepository.deleteFav(username)
        }
    }

    init {
        isFav()
        getDetailuser(username)
        getFollowersList(username)
        getFollowingList(username)
    }

}