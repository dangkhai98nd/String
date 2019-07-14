package com.example.twitter.viewmodel

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.twitter.model.DataFeed
import com.google.android.libraries.places.internal.e
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import com.example.twitter.R


class PoiViewModel(var dataFeed: DataFeed) : ViewModel() {
    //    val title = MutableLiveData<String>()
//    init {
////        title.value = dataFeed?.title
//        Log.e("poi","${dataFeed.title}")
//    }
    fun getImageUrl(): String {
        // The URL will usually come from a model (i.e Profile)
        return "http://cdn.meme.am/instances/60677654.jpg"
    }
//
//    @BindingAdapter("{app:imageUrl}")
//    fun loadImage(view: ImageView, imageUrl: String) {
//        Glide.with(view.context)
//            .load(imageUrl)
//            .into(view)
//    }

//    @BindingAdapter("fadevisible", true.toString())
//    fun setFadevisible(view: View, visible: Boolean) {
//        Log.e("Bindings", "setFadeVisible: ")
//    }


    //    companion object {
//        @BindingAdapter("bind:imageUrl")
//        fun loadImage(imageView: ImageView, isLiked: String){
////            if (isLiked)
//                Glide.with(imageView.context).load(isLiked).into(imageView)
////            else
////                Glide.with(imageView.context).load(R.drawable.ic_icosocialheartline).into(imageView)
//            Log.e("set image islike" , "true")
//
//        }
//
//    }

}