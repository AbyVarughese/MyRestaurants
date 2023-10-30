package com.example.myrestaurants.views.restaurant

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myrestaurants.main.MainApp
import com.example.myrestaurants.models.RestaurantModel
import com.example.myrestaurants.showImagePicker
import timber.log.Timber

class RestaurantPresenter(private val view: RestaurantView) {

    var restaurant = RestaurantModel()
    var app: MainApp = view.application as MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("restaurant_edit")) {
            edit = true
            restaurant = view.intent.extras?.getParcelable("restaurant_edit")!!
            view.showRestaurant(restaurant)
        }
        registerImagePickerCallback()
    }

    fun doPersist(updatedRestaurant: RestaurantModel) {
        restaurant.name = updatedRestaurant.name
        restaurant.address = updatedRestaurant.address
        restaurant.rating = updatedRestaurant.rating
        restaurant.description = updatedRestaurant.description
        if (edit) {
            app.restaurants.update(restaurant)
        } else {
            app.restaurants.create(restaurant)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.restaurants.delete(restaurant)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }

    fun cacheLastVisit (lastVisit: Long) {
        restaurant.lastVisit = lastVisit;
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            restaurant.coverPhoto = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(restaurant.coverPhoto,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(restaurant.coverPhoto)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}