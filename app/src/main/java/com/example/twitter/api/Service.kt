package com.example.twitter.api

import com.example.twitter.model.Feed
import com.example.twitter.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @POST("users-login")
    fun getApiUser(@Body body: RequestBody) :Call<User>

    @GET("feed")
    fun getApiFeed(
        @Header("Authorization") authorization : String,
        @Query("page") page : String,
        @Query("current_per_page") currentPerPage : String
    ) : Call<Feed>

}