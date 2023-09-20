package com.example.githubuser.data.retrofit

import com.example.githubuser.data.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun findUser(
        @Query("uname") uname:String,
    ): Call<SearchUserResponse>
}