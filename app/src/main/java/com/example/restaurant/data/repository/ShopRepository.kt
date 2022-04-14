package com.example.restaurant.data.repository

import com.example.restaurant.data.local.ShopDao
import com.example.restaurant.data.remote.ShopRemoteDataSource
import com.example.restaurant.usecase.performGetOperation
import javax.inject.Inject

class ShopRepository @Inject constructor(
    private val remoteDataSource: ShopRemoteDataSource,
    private val localDataSource: ShopDao
) {

    fun getShop(key: String,id:String,format:String) = performGetOperation(
        databaseQuery = { localDataSource.getShop(id) },
        networkCall = { remoteDataSource.getShop(key,id,format) },
        saveCallResult = { localDataSource.insert(it.results?.shop)}
    )

    fun getShops(key: String, large_area:String,format:String) = performGetOperation(
        databaseQuery = { localDataSource.getAllShops() },
        networkCall = { remoteDataSource.getShopList(key,large_area,format) },
        saveCallResult = { localDataSource.insertAll(it.results?.shop)}
    )
}