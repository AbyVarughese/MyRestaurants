package com.example.myrestaurants.main

import android.app.Application
import com.example.myrestaurants.models.RestaurantJsonStore
import com.example.myrestaurants.models.RestaurantStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var restaurants: RestaurantStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        restaurants = RestaurantJsonStore(applicationContext)
        i("Application started")
    }
}