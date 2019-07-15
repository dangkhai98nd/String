package com.example.twitter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.twitter.R
import com.example.twitter.databinding.ItemPhotoBinding
import com.example.twitter.model.Itinerary
import com.example.twitter.viewmodel.PhotoItineraryViewModel

class ItineraryAdapter : RecyclerView.Adapter<ItineraryAdapter.PhotoViewHolder>() {
    var itineraries: MutableList<Itinerary> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_photo,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = itineraries.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(itineraries[position])
    }

    fun addAll(itineraries: List<Itinerary>?) {
        this.itineraries.addAll(itineraries ?: emptyList())
        notifyDataSetChanged()
    }

    class PhotoViewHolder(
        private val photoBinding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(photoBinding.root) {
        fun bind(itinerary: Itinerary) {
            if (photoBinding.photoItineraryViewModel != null) {
                photoBinding.photoItineraryViewModel?.itinerary = itinerary
            } else {
                photoBinding.photoItineraryViewModel = PhotoItineraryViewModel(itinerary)
            }
            photoBinding.executePendingBindings()
        }
    }

}