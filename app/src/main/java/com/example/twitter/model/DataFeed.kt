package com.example.twitter.model

class DataFeed (
    var id : Int? = null,
    var type : String? = null,
    var title : String? = null,
    var photos : List<Photo>? = null,
    var strungCounter : Int? = null,
    var likeCounter : Int? = null,
    var commentCounter : Int? = null,
    var address : String? = null,
    var lat : Float? = null,
    var long : Float? = null,
//    var workingHours : ????
    var websiteUrl : String? = null,
    var placeID : String? = null,
    var created_at : String? = null,
    var updated_at : String? = null,
    var distance : Float? = null,
    var user :DataUser? = null,
    var isLiked : Boolean? = null,
    var tags : List<Any>? = null,       // ????
    var strungFrom : DataUser? = null,
    var description : String? = null
)