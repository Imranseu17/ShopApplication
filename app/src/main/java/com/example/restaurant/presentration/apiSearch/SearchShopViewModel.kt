package com.example.restaurant.presentration.apiSearch

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.restaurant.data.repository.ShopRepository

class SearchShopViewModel @ViewModelInject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    var keyword = ""

    val shopSearchList = repository.searchShopList("f5c06c8896e2d2d4",keyword,"json")
}