package com.example.twitter.viewmodel

import androidx.lifecycle.ViewModel
import com.example.twitter.adapter.ItineraryAdapter
import com.example.twitter.model.DataFeed

class ItineraryViewModel(var dataFeed: DataFeed) : ViewModel() {
    var strNomal: String = "nomal"

}