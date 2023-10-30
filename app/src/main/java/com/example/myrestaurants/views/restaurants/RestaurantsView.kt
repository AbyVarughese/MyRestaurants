package com.example.myrestaurants.views.restaurants

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrestaurants.R
import com.example.myrestaurants.databinding.ActivityRestaurantsBinding
import com.example.myrestaurants.main.MainApp
import com.example.myrestaurants.models.RestaurantModel

class RestaurantsView : AppCompatActivity(), RestaurantListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRestaurantsBinding
    private lateinit var presenter: RestaurantsPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivityRestaurantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        presenter = RestaurantsPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadRestaurants()

        binding.fabAdd.setOnClickListener {
            presenter.doAddRestaurant()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRestaurantClick(restaurant: RestaurantModel, position: Int) {
        this.position = position
        presenter.doEditRestaurant(restaurant, this.position)
    }

    private fun loadRestaurants() {
        binding.recyclerView.adapter = RestaurantAdapter(presenter.getRestaurants(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getRestaurants().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}