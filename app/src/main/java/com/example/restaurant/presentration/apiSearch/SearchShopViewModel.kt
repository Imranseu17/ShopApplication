package com.example.restaurant.presentration.apiSearch

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.restaurant.data.entities.Shop
import com.example.restaurant.data.repository.ShopRepository
import com.example.restaurant.usecase.Resource

class SearchShopViewModel @ViewModelInject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    private val _keyword = MutableLiveData<String>()


    private val _character = _keyword.switchMap { keyword ->
        repository.searchShopList("f5c06c8896e2d2d4","青物横丁","json")
    }
    val character: LiveData<Resource<List<Shop>>> = _character


    fun start(keyword: String) {
        _keyword.value = keyword
    }
//    var keyword = ""
//    val character = repository.searchShopList("f5c06c8896e2d2d4",keyword,"json")
}