package com.example.githubuser.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.githubuser.data.dao.FavoriteUserDao
import com.example.githubuser.data.entity.FavoriteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavUserRepository private constructor(
    private val favUserDao:FavoriteUserDao
){
    fun getFavList(): LiveData<List<FavoriteUser>> = favUserDao.getAllFavUser()

    suspend fun isFav(username:String?) = favUserDao.isFavorite(username)

    suspend fun addFav(user: FavoriteUser) {
        withContext(Dispatchers.IO) {
            if (!isFav(user.username)) {
                favUserDao.insert(user)
            } else {
                Log.e("UsersRepository", "User Already in Favorite!")
            }
        }
    }

    suspend fun deleteFav(username: String?) {
        withContext(Dispatchers.IO){
            if (isFav(username = username )) {
                favUserDao.delete(username)
            } else {
                Log.e("UsersRepository", "User is Not in Favorite!")
            }
        }
    }

    companion object {
        @Volatile
        private var instance: FavUserRepository? = null
        fun getInstance(
            favUserDao: FavoriteUserDao
        ): FavUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavUserRepository(favUserDao)
            }.also { instance = it }
    }
}