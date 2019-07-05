package com.example.twitter.model

class Feed (
    var status : Boolean? = null,
    var message : String? = null,
    var code : Int? = null,
    var data : List<DataFeed>? = null,
    var metadata : Metadata? = null
)