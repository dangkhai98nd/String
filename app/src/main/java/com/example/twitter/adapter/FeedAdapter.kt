package com.example.twitter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twitter.R
import com.example.twitter.model.DataFeed
import kotlinx.android.extensions.LayoutContainer

class FeedAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataFeed: MutableList<DataFeed> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val type : String? = dataFeed[viewType].type
        if (type == "itinerary")
        {
            return ItemItineraryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_itinerary,parent,false))
        }
        else if (type == "post")
        {
            return ItemPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false))
        }
        else {
            return ItemPoiViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_poi,parent,false))
        }
    }

    override fun getItemCount(): Int = dataFeed.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ItemItineraryViewHolder -> {

            }
            is ItemPostViewHolder -> {

            }
            else -> {

            }
        }

    }

    fun addAll(dataFeedAdd: List<DataFeed>) {
        this.dataFeed.addAll(dataFeedAdd)
        notifyDataSetChanged()
    }


}

class ItemPoiViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

}

class ItemPostViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView) , LayoutContainer{

}

class ItemItineraryViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

}
