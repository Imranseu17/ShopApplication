package com.example.restaurant.data.remote

import javax.inject.Inject

class ShopRemoteDataSource @Inject constructor(
    private val shopService: ShopService
): BaseDataSource() {

    suspend fun getShopList(key:String, large_area:String,format:String) =
        getResult { shopService.getAllShops(key,large_area,format) }

    suspend fun getShop(key:String, id:String,format:String) =
        getResult { shopService.getShop(key,id,format) }

 suspend fun searchShopList(key:String, keyword:String,format:String) =
        getResult { shopService.searchShopList(key,keyword,format) }

 suspend fun findNearbyRestaurantOfJapanese(key:String, lat:Double,lng:Double,range: String,format:String) =
        getResult { shopService.findNearbyRestaurantOfJapanese(key,lat,lng,range,format) }
}