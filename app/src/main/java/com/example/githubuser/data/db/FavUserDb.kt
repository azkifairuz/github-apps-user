package com.example.githubuser.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.data.dao.FavoriteUserDao
import com.example.githubuser.data.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavUserDb: RoomDatabase() {
    abstract fun favUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: FavUserDb? = null

        fun getInstance(context: Context): FavUserDb =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavUserDb::class.java,"favorite_user_db"
                ).build()
            }
    }
}