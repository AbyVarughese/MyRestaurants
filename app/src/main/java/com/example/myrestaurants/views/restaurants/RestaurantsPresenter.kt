package com.example.myrestaurants.views.restaurants

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myrestaurants.activities.RestaurantsMapActivity
import com.example.myrestaurants.views.restaurant.RestaurantView
import com.example.myrestaurants.main.MainApp
import com.example.myrestaurants.models.RestaurantModel

class RestaurantsPresenter(private val view: RestaurantsView) {

    var app: MainApp = view.application as MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getRestaurants() = app.restaurants.findAll()

    fun doAddRestaurant() {
        val launcherIntent = Intent(view, RestaurantView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditRestaurant(restaurant: RestaurantModel, pos: Int) {
        val launcherIntent = Intent(view, RestaurantView::class.java)
        launcherIntent.putExtra("restaurant_edit", restaurant)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowRestaurantMap() {
        val launcherIntent = Intent(view, RestaurantsMapActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) {
                    view.onRefresh()
                } else{
                    // Deleting
                    if (it.resultCode == 99) { view.onDelete(position)}
                }
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}