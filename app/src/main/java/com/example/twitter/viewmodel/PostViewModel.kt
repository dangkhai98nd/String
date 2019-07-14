package com.example.twitter.viewmodel

import androidx.databinding.BaseObservable
import com.example.twitter.model.DataFeed

class PostViewModel (var dataFeed: DataFeed) : BaseObservable() {
    var strNomal : String = "nomal"
}