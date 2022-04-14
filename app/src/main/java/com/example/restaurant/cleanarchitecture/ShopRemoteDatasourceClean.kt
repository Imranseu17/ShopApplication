package com.example.restaurant.cleanarchitecture



import javax.inject.Inject

class ShopRemoteDatasourceClean  @Inject constructor(
    private val shopServiceClean: ShopServiceClean
): ShopBaseDataSourceClean() {

  fun getShopList(key:String, large_area:String,format:String) =
        getResult { shopServiceClean.getAllShops(key,large_area,format) }

     fun getShop(key:String, id:String,format:String) =
        getResult { shopServiceClean.getShop(key,id,format) }
}