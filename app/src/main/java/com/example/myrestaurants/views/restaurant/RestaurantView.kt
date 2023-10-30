package com.example.myrestaurants.views.restaurant

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.example.myrestaurants.R
import com.example.myrestaurants.databinding.ActivityRestaurantBinding
import com.example.myrestaurants.models.RestaurantModel
import timber.log.Timber.Forest.i
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class RestaurantView : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var presenter: RestaurantPresenter
    var restaurant = RestaurantModel()
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = RestaurantPresenter(this)
        binding.coverPhoto.setOnClickListener {
            presenter.doSelectImage()
        }
        binding.lastVisit.setOnClickListener {
            showDatePicker()
        }
/*
        binding.chooseImage.setOnClickListener {
            presenter.cacheRestaurant(binding.restaurantName.text.toString(), binding.description.text.toString())
            presenter.doSelectImage()
        }
        */
/*
        binding.restaurantLocation.setOnClickListener {
            presenter.cacheRestaurant(binding.restaurantName.text.toString(), binding.description.text.toString())
            presenter.doSetLocation()
        }
*/
        binding.btnAdd.setOnClickListener {
            if (binding.restaurantName.text.toString().isEmpty()) {
                Snackbar.make(binding.root, R.string.enter_restaurantName, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                val updatedRestaurant = RestaurantModel()
                updatedRestaurant.name = binding.restaurantName.text.toString()
                updatedRestaurant.address = binding.address.text.toString()
                updatedRestaurant.rating = 0
                if(binding.radioButton1.isChecked) { updatedRestaurant.rating = 1 }
                if(binding.radioButton2.isChecked) { updatedRestaurant.rating = 2 }
                if(binding.radioButton3.isChecked) { updatedRestaurant.rating = 3 }
                if(binding.radioButton4.isChecked) { updatedRestaurant.rating = 4 }
                if(binding.radioButton5.isChecked) { updatedRestaurant.rating = 5 }
                updatedRestaurant.description = binding.description.text.toString()
                presenter.doPersist(updatedRestaurant)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_restaurant, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showRestaurant(restaurant: RestaurantModel) {
        binding.restaurantName.setText(restaurant.name)
        binding.description.setText(restaurant.description)
        binding.address.setText(restaurant.address)

        when(restaurant.rating) {
            1 -> binding.radioButton1.isChecked = true
            2 -> binding.radioButton2.isChecked = true
            3 -> binding.radioButton3.isChecked = true
            4 -> binding.radioButton4.isChecked = true
            5 -> binding.radioButton5.isChecked = true
        }

        binding.btnAdd.setText(R.string.save_restaurant)
        if (restaurant.coverPhoto != Uri.EMPTY) {
            Picasso.get()
                .load(restaurant.coverPhoto)
                .into(binding.coverPhoto)
        }

    }

    fun updateImage(image: Uri){
        i("Cover photo added")
        Picasso.get()
            .load(image)
            .into(binding.coverPhoto)
    }

    fun showDatePicker() {
        DatePickerDialog(
            this,
            {DatePicker, year:Int, month:Int, date:Int ->
                val lastVisit = LocalDateTime.of(year, month, date, 0, 0)
                binding.lastVisit.text = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(lastVisit)
                presenter.cacheLastVisit(lastVisit.toEpochSecond(ZoneOffset.UTC))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}