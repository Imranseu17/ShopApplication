package com.example.restaurant.presentration.nearbyPlaces

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.restaurant.data.repository.ShopRepository

class NearbyPlacesViewModel @ViewModelInject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    val nearbyPlacesSearch =
        repository.findNearbyRestaurantOfJapanese("f5c06c8896e2d2d4",35.669220,
            139.761457,"3000m", "json")
}