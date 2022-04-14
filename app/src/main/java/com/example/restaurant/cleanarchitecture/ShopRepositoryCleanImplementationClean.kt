package com.example.restaurant.cleanarchitecture



import javax.inject.Inject

class ShopRepositoryCleanImplementationClean @Inject constructor(
    private val remoteDataSource: ShopRemoteDatasourceClean
) :ShopRepositoryClean{
    override fun getShopList(key: String, large_area: String, format: String){

     remoteDataSource.getShopList(key, large_area, format)
    }

    override fun getShop(key: String, id: String, format: String){
        remoteDataSource.getShop(key, id, format)
    }
}