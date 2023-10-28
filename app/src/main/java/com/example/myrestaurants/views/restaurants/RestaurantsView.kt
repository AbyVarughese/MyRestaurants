package com.example.myrestaurants.views.restaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.myrestaurants.R
import com.example.myrestaurants.databinding.ActivityRestaurantsBinding

class RestaurantsView : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fabAdd.setOnClickListener {
            val greetingText = getString(R.string.greeting_text)
            Toast.makeText(applicationContext, greetingText, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}