package com.example.twitter.viewmodel

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.twitter.R
import com.example.twitter.model.DataFeed

class PoiViewModel(var dataFeed: DataFeed) : BaseObservable() {
    //    val title = MutableLiveData<String>()
//    init {
////        title.value = dataFeed?.title
//        Log.e("poi","${dataFeed.title}")
//    }
//    fun getLikeProfile() : Boolean{
//        return dataFeed.isLiked ?: false
//    }


//    @BindingAdapter("isLiked")
//    private fun setIsLiked(imageView: ImageView, isLiked: Boolean?) {
//        if (dataFeed.isLiked == true)
//            Glide.with(imageView.context).load(R.drawable.ic_heartfilledaccent).into(imageView)
//        else
//            Glide.with(imageView.context).load(R.drawable.ic_icosocialheartline).into(imageView)
//        Log.e("set image islike" , "true")
//    }
}