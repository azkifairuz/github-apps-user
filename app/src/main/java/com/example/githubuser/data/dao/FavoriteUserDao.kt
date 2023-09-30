package com.example.githubuser.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuser.data.entity.FavoriteUser

interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavoriteUser)

    @Query("SELECT * from favorite_user ORDER BY id ASC ")
    fun getAllFavUser(): LiveData<List<FavoriteUser>>
}