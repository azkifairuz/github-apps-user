package com.example.githubuser.data.retrofit

import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun findUser(
        @Query("q") uname:String,
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("/users/{username}/followers")
    fun getFollowersList(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("/users/{username}/following")
    fun getFollowingList(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}