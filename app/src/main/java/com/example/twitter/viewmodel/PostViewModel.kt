package com.example.twitter.viewmodel

import androidx.lifecycle.ViewModel
import com.example.twitter.model.DataFeed

class PostViewModel(var dataFeed: DataFeed) : ViewModel() {
    var strNomal: String = "nomal"
}