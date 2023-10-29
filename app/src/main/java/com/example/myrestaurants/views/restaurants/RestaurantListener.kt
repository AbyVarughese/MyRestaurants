package com.example.myrestaurants.views.restaurants

import com.example.myrestaurants.models.RestaurantModel

interface RestaurantListener {
    fun onRestaurantClick(restaurant: RestaurantModel, position : Int)
}