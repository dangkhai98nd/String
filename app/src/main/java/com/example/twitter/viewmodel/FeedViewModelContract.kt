package com.example.twitter.viewmodel

import com.example.twitter.model.DataFeed

interface FeedViewModelContract {
    fun loadDataSuccess(listFeed : List<DataFeed>?)
}