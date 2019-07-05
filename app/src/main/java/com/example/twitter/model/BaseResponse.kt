package com.example.twitter.model

abstract class BaseResponse(
    var status: Boolean? = null,
    var message: String? = null,
    var code: Int? = null,
    var metadata: Metadata? = null
)