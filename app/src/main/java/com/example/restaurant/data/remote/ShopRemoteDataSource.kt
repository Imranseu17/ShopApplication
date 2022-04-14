package com.example.restaurant.data.remote

import javax.inject.Inject

class ShopRemoteDataSource @Inject constructor(
    private val shopService: ShopService
): BaseDataSource() {

    suspend fun getShopList(key:String, large_area:String,format:String) =
        getResult { shopService.getAllShops(key,large_area,format) }

    suspend fun getShop(key:String, id:String,format:String) =
        getResult { shopService.getShop(key,id,format) }
}