package com.example.myrestaurants.models

interface RestaurantStore {
    fun findAll(): List<RestaurantModel>
    fun findById(id:Long) : RestaurantModel?
    fun create(restaurant: RestaurantModel)
    fun update(restaurant: RestaurantModel)
    fun delete(restaurant: RestaurantModel)
}