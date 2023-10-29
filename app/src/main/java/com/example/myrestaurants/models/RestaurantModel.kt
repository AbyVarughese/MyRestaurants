package com.example.myrestaurants.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantModel(
    var id: Long = 0,
    var name: String = "",
    var address: String = "",
    var description: String = "",
    var lastVisit: Long = 0,
    var rating: Int = 0,
    var coverPhoto: Uri = Uri.EMPTY,
    var lat : Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable