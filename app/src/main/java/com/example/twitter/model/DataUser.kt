package com.example.twitter.model

import java.sql.Time

class DataUser (
    var id : Int? = null,
    var email : String? = null,
    var username : String? = null,
    var name : String? = null,
    var date_of_birth : String? = null,
    var facebookID : String? = null,            // ?????
    var profilePhoto : String? = null,          // ?????
    var gender : String? = null,                // ?????
    var notificationSettings : NotificationSettings? = null,
    var bio : String? = null, // ????
    var website : String? = null,
    var type : String? = null,
    var trash : Boolean? = null,
    var isActive : Boolean? = null,
    var isSupperUser : Boolean? = null,
    var currentLocation : String? = null,
    var isNewUser : Boolean? = null,
    var created_at : String? = null,
    var updated_at : String? = null,
    var access_token : String? = null,
    var refresh_token : String? = null,
    var badge : String? = null,     // ????
    var wanderlust_details : WanderlustDetails? = null
)