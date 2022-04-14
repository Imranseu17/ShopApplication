package com.example.restaurant.presentration.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.restaurant.data.repository.ShopRepository

class ShopViewModel @ViewModelInject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    val shops = repository.getShops("f5c06c8896e2d2d4","Z011","json")
}