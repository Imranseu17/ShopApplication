package com.example.restaurant.cleanarchitecture



interface ShopRepositoryClean {
    fun getShopList(key:String, large_area:String,format:String)

    fun getShop(key:String, id:String,format:String)

}