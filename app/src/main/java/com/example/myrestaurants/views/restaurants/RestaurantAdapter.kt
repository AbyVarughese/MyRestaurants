package com.example.myrestaurants.views.restaurants

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.myrestaurants.databinding.CardRestaurantBinding
import com.example.myrestaurants.models.RestaurantModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class RestaurantAdapter constructor(private var restaurants: List<RestaurantModel>,
                                    private val listener: RestaurantListener
) : RecyclerView.Adapter<RestaurantAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val restaurant = restaurants[holder.adapterPosition]
        holder.bind(restaurant, listener)
    }

    override fun getItemCount(): Int = restaurants.size

    class MainHolder(private val binding : CardRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: RestaurantModel, listener: RestaurantListener) {
            binding.restaurantName.text = restaurant.name
            if(restaurant.description.isNotEmpty()) {
                binding.description.text = restaurant.description
                binding.description.visibility = View.VISIBLE
            }
            if(restaurant.lastVisit == 0L) {
                binding.lastVisit.text =  "not visited yet"
            }else{
                binding.lastVisit.text = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(LocalDateTime.ofEpochSecond(restaurant.lastVisit, 0, ZoneOffset.UTC))
            }
            binding.distance.text = "0m away"
            binding.rating.text = "⭐".repeat(restaurant.rating).padStart(5, '☆')
            if(restaurant.coverPhoto != Uri.EMPTY) {
                Picasso.get().load(restaurant.coverPhoto).resize(200, 200).into(binding.coverPhoto)
            }
            binding.root.setOnClickListener { listener.onRestaurantClick(restaurant,adapterPosition) }
        }
    }
}
