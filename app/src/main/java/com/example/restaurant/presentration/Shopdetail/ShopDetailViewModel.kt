package com.example.restaurant.presentration.Shopdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.restaurant.data.entities.Shop
import com.example.restaurant.data.repository.ShopRepository
import com.example.restaurant.usecase.Resource

class ShopDetailViewModel @ViewModelInject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    private val _id = MutableLiveData<String>()


    private val _character = _id.switchMap { id ->
        repository.getShop("f5c06c8896e2d2d4",id,"json")
    }
    val character: LiveData<Resource<Shop>> = _character


    fun start(id: String) {
        _id.value = id
    }

}
