package com.example.twitter.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    companion object {
        var BASE_URL : String = "http://string-api.vinova.sg/api/"
        var retrofit : Retrofit? = null
        fun getClient () : Retrofit? {
            if (retrofit == null)
            {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}