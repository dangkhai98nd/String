package com.example.twitter.viewmodel

import android.app.Application
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitter.api.Client
import com.example.twitter.api.Service
import com.example.twitter.model.DataFeed
import com.example.twitter.model.Feed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel : ViewModel() {
    //    private var feedViewModelContract : FeedViewModelContract = mContext as FeedViewModelContract
    var listDataFeed: MutableLiveData<List<DataFeed>> = MutableLiveData()
    var page = 1
    var totalPages = 1
    val currentPerPage = 10
    fun loadData(access_token : String?) : Boolean {
        Log.e("start load page","$page")
        if (page > totalPages) return true
        try {

            val service: Service? = Client.getClient()?.create(Service::class.java)

            val call: Call<Feed>? = service?.getApiFeed("Bearer $access_token", page.toString(), currentPerPage.toString())

            call?.run {
                enqueue(object : Callback<Feed> {
                    override fun onFailure(call: Call<Feed>, t: Throwable) {
                        Log.e("load data page","$page failed")
//                        loadData(access_token)
                    }

                    override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                        if (response.body()?.status != true) {
//                            Toast.makeText(mApplication, "failed", Toast.LENGTH_SHORT).show()

                            return
                        }
                        listDataFeed.value = response.body()?.data ?: listOf()
                        totalPages = response.body()?.metadata?.total_pages ?:
//                        Log.e("id", "${listDataFeed.value.get(0).id}")
                        Log.e("message", "${response.body()?.status}")
//                        Toast.makeText(mContext, "${response.body()?.status}", Toast.LENGTH_SHORT).show()
                        Log.e("load done page" ,"$page")
                        page += 1
//                        feedViewModelContract.loadDataSuccess(listDataFeed)

                    }
                })
            }

        } catch (e: Exception) {
            Log.d("Error ", e.message)
        }
        return false
    }

}