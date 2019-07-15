package com.example.twitter.adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitter.R

class BindingAdapterUtils {
    companion object {
        @BindingAdapter("isLiked")
        @JvmStatic
        fun ImageView.setImageIsLiked(isLiked: Boolean?) {
            if (isLiked == true)
                Glide.with(context)
                    .load(R.drawable.ic_heartfilledaccent)
                    .into(this)
            else
                Glide.with(context)
                    .load(R.drawable.ic_icosocialheartline)
                    .into(this)

        }

        @BindingAdapter("profilePhotoUrl")
        @JvmStatic
        fun ImageView.setProfilePhotoUrl(url: String?) {
            if (url != null) {
                Glide.with(context)
                    .load(url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            } else {
                Glide.with(context)
                    .load(R.drawable.ic_rectangle)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            }
        }

        @BindingAdapter("photoUrl")
        @JvmStatic
        fun ImageView.setPhotoUrl(url : String?){
            if (url != null){
                Glide.with(context)
                    .load(url)
                    .into(this)
            }
            else {
                Glide.with(context)
                    .load(R.drawable.ic_rectangle)
                    .into(this)
            }
        }

    }
}