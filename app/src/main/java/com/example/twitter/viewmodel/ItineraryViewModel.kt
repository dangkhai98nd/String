package com.example.twitter.viewmodel

import androidx.databinding.BaseObservable
import com.example.twitter.model.DataFeed

class ItineraryViewModel(var dataFeed: DataFeed) : BaseObservable() {
    var strNomal : String = "nomal"
}