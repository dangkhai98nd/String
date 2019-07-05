package com.example.twitter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.example.twitter.api.Client
import com.example.twitter.api.Service
import com.example.twitter.model.DataFeed
import com.example.twitter.model.Feed
import com.example.twitter.model.User

import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : AppCompatActivity() {

    private var access_token : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        access_token =intent?.extras?.getString("access_token")

        Toast.makeText(this@FeedActivity,"$access_token",Toast.LENGTH_SHORT).show()
        loadJSON()

    }


    fun loadJSON()
    {
        try {

            val service: Service? = Client.getClient()?.create(Service::class.java)

            val call : Call<Feed>? = service?.getApiFeed("Bearer $access_token","1","25")

            call?.run {
                enqueue(object : Callback<Feed> {
                    override fun onFailure(call: Call<Feed>, t: Throwable) {
                        loadJSON()
                    }

                    override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                        if (response.body()?.status != true)
                        {
                            Toast.makeText(this@FeedActivity,"faild",Toast.LENGTH_SHORT).show()
                            return
                        }
                        val dataFeed : List<DataFeed>? = response.body()?.data
                        Log.e("id","${dataFeed?.get(0)?.id}")
                        Log.e("message","${response.body()?.status}")

                    }
                })
            }

        } catch (e: Exception) {
            Log.d("Error ", e.message)
        }
    }

}
