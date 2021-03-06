package com.example.restaurant.data.remote


import com.example.restaurant.data.entities.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ShopService {

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
    suspend fun getAllShops(@Query("key") key:String,
                            @Query("large_area") large_area:String,
                            @Query("format")format:String)
    :Response<Root>

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
    suspend fun getShop(
        @Query("key") key:String,
        @Query("id") id:String,
        @Query("format")format:String
    ):Response<Root>

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
  suspend  fun searchShopList(
        @Query("key") key:String,
        @Query("keyword") keyword:String,
        @Query("format")format:String
    ):Response<Root>

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
  suspend fun findNearbyRestaurantOfJapanese(
        @Query("key") key:String,
        @Query("lat") lat:Double,
        @Query("lng") lng:Double,
        @Query("range") range: String,
        @Query("format")format:String
    ):Response<Root>
}