package com.example.myrestaurants.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.example.myrestaurants.helpers.*
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "myrestaurants.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<RestaurantModel>>() {}.type

class RestaurantJsonStore(private val context: Context) : RestaurantStore {

    var restaurants = mutableListOf<RestaurantModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RestaurantModel> {
        return restaurants
    }

    override fun findById(id: Long): RestaurantModel? {
        return restaurants.find { it.id == id }
    }

    override fun create(restaurant: RestaurantModel) {
        var restaurantId = 1L
        if(restaurants.isNotEmpty()) {
            restaurantId = restaurants.last().id + 1
        }
        restaurant.id = restaurantId
        restaurants.add(restaurant)
        serialize()
    }

    override fun update(restaurant: RestaurantModel) {
        val restaurants = findAll() as ArrayList<RestaurantModel>
        val r: RestaurantModel? = restaurants.find { p -> p.id == restaurant.id }
        if (r != null) {
            r.name = restaurant.name
            r.address = restaurant.address
            r.lastVisit = restaurant.lastVisit
            r.rating = restaurant.rating
            r.description = restaurant.description
            r.coverPhoto = restaurant.coverPhoto
            r.lat = restaurant.lat
            r.lng = restaurant.lng
            r.zoom = restaurant.zoom
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(restaurants, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        restaurants = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(restaurant: RestaurantModel) {
        restaurants.remove(restaurant)
        serialize()
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}