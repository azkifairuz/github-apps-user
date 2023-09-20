package com.example.githubuser.data.retrofit

import com.example.githubuser.data.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.Call
import retrofit2.http.Path

interface ApiService {
    @GET("user/{username}")
    @Headers("Authorization: token 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")
    fun findUser(
        @Path("username") username: String
    ): Call<SearchUserResponse>
}