package com.example.twitter.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitter.R
import com.example.twitter.databinding.ItemItineraryBinding
import com.example.twitter.databinding.ItemPoiBinding
import com.example.twitter.databinding.ItemPostBinding
import com.example.twitter.model.DataFeed
import com.example.twitter.viewmodel.ItineraryViewModel
import com.example.twitter.viewmodel.PoiViewModel
import com.example.twitter.viewmodel.PostViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.io.ByteArrayOutputStream


class FeedAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataFeed: MutableList<DataFeed> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (dataFeed[position].type) {
            "itinerary" -> 0
            "post" -> 1
            else -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ItemItineraryViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_itinerary, parent, false)
            )

            1 -> ItemPostViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
            )
            else -> ItemPoiViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_poi, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = dataFeed.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemItineraryViewHolder -> {
                holder.bind(dataFeed[position])
            }
            is ItemPostViewHolder -> {
                holder.bind(dataFeed[position])
            }
            is ItemPoiViewHolder -> {
                holder.bind(dataFeed[position])
            }
            else -> {
                Log.e("error", "error")
            }
        }
    }

    fun addAll(dataFeedAdd: List<DataFeed>) {
        this.dataFeed.addAll(dataFeedAdd)

        notifyDataSetChanged()
    }


    inner class ItemPoiViewHolder(
        private val itemPoiBinding: ItemPoiBinding
    ) : RecyclerView.ViewHolder(itemPoiBinding.root) {
        val mView: View = itemPoiBinding.root
        var image: ImageView = mView.findViewById(R.id.image_view)

        fun bitmapToByte(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()
            return byteArray
        }

        fun loadImageFromPlaceid(dataFeed: DataFeed) {
            var bitmap: Bitmap? = null
            val placeId = dataFeed.placeID
            val fields: List<Place.Field> = listOf(Place.Field.PHOTO_METADATAS)

            val placeRequest = FetchPlaceRequest.builder(placeId ?: return, fields).build()
            val placesClient: PlacesClient = Places.createClient(mContext)
            placesClient.fetchPlace(placeRequest).addOnSuccessListener { response ->
                val place = response.place

                val photoMetadata = place.photoMetadatas!![0]

                val attributions = photoMetadata.attributions

                val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(500) // Optional.
                    .setMaxHeight(300) // Optional.
                    .build()
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener { fetchPhotoResponse ->
                    bitmap = fetchPhotoResponse.bitmap
                    image.setImageBitmap(bitmap)
//                    Glide.with(mView)
//                        .asBitmap()
//                        .load(bitmapToByte(bitmap))
//                        .thumbnail(Glide.with(mView).asBitmap().load(R.drawable.icon_load))
//                        .fitCenter()
//                        .into(object : SimpleTarget<Bitmap> (){
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            image.setImageBitmap(resource)
//                        }
//                    })

                }.addOnFailureListener { exception ->
                    if (exception is ApiException) {
                        val apiException = exception as ApiException
                        val statusCode = apiException.statusCode
                        // Handle error with given status code.
                        Log.e("error", "Place not found: $exception")
//                        abc(placesClient, photoRequest)
                        Glide.with(mView)
                            .load(R.drawable.ic_rectangle)
                            .into(image)
                    }
                }
            }
//            Glide.with(mView).load(bitmap).into(image)
        }

        fun abc(placesClient: PlacesClient, photoRequest: FetchPhotoRequest) {
            placesClient.fetchPhoto(photoRequest).addOnSuccessListener { fetchPhotoResponse ->
                var bitmap = fetchPhotoResponse.bitmap
                image.setImageBitmap(bitmap)
//                    Glide.with(mView)
////                        .asBitmap()
////                        .load(bitmapToByte(bitmap))
//                        .thumbnail(Glide.with(mView).asBitmap().load(R.drawable.icon_load))
//                        .fitCenter()
//                        .into(object : SimpleTarget<Bitmap> (){
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            image.setImageBitmap(resource)
//                        }
//                    })

            }.addOnFailureListener { exception ->
                if (exception is ApiException) {
                    val apiException = exception as ApiException
                    val statusCode = apiException.statusCode

                    Log.e("error", "Place not found: $exception")
//                    abc(placesClient, photoRequest)
                }
            }
        }


//        private fun getPhoto(placeid : String)
//        {
//            val mGeoDataClient : GeoDataClient = com.google.android.gms.location.places.Places.getGeoDataClient(mContext)
//            val photoMetadataResponse : Task<PlacePhotoMetadataResponse>? = mGeoDataClient.getPlacePhotos(placeid)
//            photoMetadataResponse?.addOnCompleteListener(object : OnCompleteListener<PlacePhotoMetadataResponse> {
//                override fun onComplete(p0: Task<PlacePhotoMetadataResponse>) {
//                    val photos : PlacePhotoMetadataResponse = p0.result ?: return
//                    val photoMetadataBuffer : PlacePhotoMetadataBuffer = photos.photoMetadata
//                    val photoMetadata : PlacePhotoMetadata = photoMetadataBuffer.get(0)
//                    var attribution : CharSequence? = photoMetadata.attributions
//                    val photoResponse : Task<PlacePhotoResponse> = mGeoDataClient.getPhoto(photoMetadata)
//                    photoResponse.addOnCompleteListener(object : OnCompleteListener<PlacePhotoResponse> {
//                        override fun onComplete(p1: Task<PlacePhotoResponse>) {
//                            val photo : PlacePhotoResponse = p1.result ?: return
//                            val bitmap : Bitmap = photo.bitmap
//                            image.setImageBitmap(bitmap)
//                        }
//                    })
//                }
//            })
//        }


        fun bind(dataFeed: DataFeed) {
            if (itemPoiBinding.poiViewModel == null)
                itemPoiBinding.poiViewModel = PoiViewModel(dataFeed)
            else itemPoiBinding.poiViewModel?.dataFeed = dataFeed
//            Log.e("poi", "${itemPoiBinding.poiViewModel?.dataFeed?.title}")
            itemPoiBinding.executePendingBindings()
//            getPhoto(dataFeed.placeID ?: return)
            loadImageFromPlaceid(dataFeed)
        }
    }

    class ItemPostViewHolder(
        private val itemPostBinding: ItemPostBinding
    ) : RecyclerView.ViewHolder(itemPostBinding.root) {
        fun bind(dataFeed: DataFeed) {
            if (itemPostBinding.postViewModel == null)
                itemPostBinding.postViewModel = PostViewModel(dataFeed)
            else itemPostBinding.postViewModel?.dataFeed = dataFeed
            itemPostBinding.executePendingBindings()
        }
    }

    class ItemItineraryViewHolder(
        private val itemItineraryBinding: ItemItineraryBinding
    ) : RecyclerView.ViewHolder(itemItineraryBinding.root) {
        fun bind(dataFeed: DataFeed) {
            if (itemItineraryBinding.itemItinerary == null)
                itemItineraryBinding.itemItinerary = ItineraryViewModel(dataFeed)
            else itemItineraryBinding.itemItinerary?.dataFeed = dataFeed
            itemItineraryBinding.executePendingBindings()
        }
    }
}

