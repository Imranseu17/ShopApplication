package com.example.restaurant.domain.callback


import com.example.restaurant.data.entities.Root

interface RestaurantSearchApI {
    fun onSuccess(root: Root?, code: Int)
    fun onError(error: String?, code: Int)
}