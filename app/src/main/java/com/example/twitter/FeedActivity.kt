package com.example.twitter

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitter.adapter.FeedAdapter
import com.example.twitter.api.Client
import com.example.twitter.api.Service
import com.example.twitter.model.DataFeed
import com.example.twitter.model.Feed
import com.example.twitter.utils.EndlessRecyclerViewScrollListener
import com.example.twitter.viewmodel.FeedViewModel
import com.example.twitter.viewmodel.FeedViewModelContract
import kotlinx.android.synthetic.main.content_feed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : AppCompatActivity(), FeedViewModelContract {


    private var access_token: String? = null
    private var feedViewModel: FeedViewModel? = null
    //    private var mActivityFeedBinding : ActivityFeedBinding? = null
    private var feedAdapter: FeedAdapter? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var isLoading = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
//        Places.initialize(this@FeedActivity,"AIzaSyCtggjLY1pcqklWJHhLnPcc87wQV8-GyJI")
        access_token = intent?.extras?.getString("access_token")
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        setupListFeed()

        val layoutManager = rvFeed.layoutManager



        feedViewModel?.listDataFeed?.observe(this, Observer {
            feedAdapter?.addAll(it)
        })
//        feedViewModel?.loadData()?.observe(this, Observer {
//            feedAdapter?.addAll(it)
//        })
        if (feedViewModel?.listDataFeed?.value.isNullOrEmpty())
            isLoading = feedViewModel?.loadData(access_token) ?: false


        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Log.e("end recycleview", "true")
                if (!(isLoading)) {
                    isLoading = true

                    isLoading = feedViewModel?.loadData(access_token) ?: false

                }

            }
        }
        rvFeed.addOnScrollListener(scrollListener as RecyclerView.OnScrollListener)

    }

    private fun setupListFeed() {
        feedAdapter = FeedAdapter(this@FeedActivity)
        rvFeed.itemAnimator = DefaultItemAnimator()
        rvFeed.adapter = feedAdapter
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            rvFeed.layoutManager = LinearLayoutManager(this)
        else rvFeed.layoutManager = GridLayoutManager(this, 2)
    }

//    private fun initBinding() {
//        mActivityFeedBinding = DataBindingUtil.setContentView(this,R.layout.activity_feed)
//        feedViewModel = FeedViewModel(this@FeedActivity,access_token)
//        mActivityFeedBinding.apply {
//            this?.lifecycleOwner = this@FeedActivity
//            this?.feedViewModel = feedViewModel
//        }
//    }


    fun loadJSON() {
        try {

            val service: Service? = Client.getClient()?.create(Service::class.java)

            val call: Call<Feed>? = service?.getApiFeed("Bearer $access_token", "1", "25")

            call?.run {
                enqueue(object : Callback<Feed> {
                    override fun onFailure(call: Call<Feed>, t: Throwable) {
                        loadJSON()
                    }

                    override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                        if (response.body()?.status != true) {
                            Toast.makeText(this@FeedActivity, "faild", Toast.LENGTH_SHORT).show()
                            return
                        }
                        val dataFeed: List<DataFeed>? = response.body()?.data
                        Log.e("id", "${dataFeed?.get(0)?.id}")
                        Log.e("message", "${response.body()?.status}")

                    }
                })
            }

        } catch (e: Exception) {
            Log.d("Error ", e.message)
        }
    }

    override fun loadDataSuccess(listFeed: List<DataFeed>?) {
        feedAdapter?.addAll(listFeed ?: return)
    }

}
