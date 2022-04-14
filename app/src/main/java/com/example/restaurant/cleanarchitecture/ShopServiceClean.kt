package com.example.restaurant.cleanarchitecture


import com.example.restaurant.data.entities.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ShopServiceClean {
    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
    fun getAllShops(@Query("key") key:String,
                            @Query("large_area") large_area:String,
                            @Query("format")format:String)
            : Response<Root>

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
    fun getShop(
        @Query("key") key:String,
        @Query("id") id:String,
        @Query("format")format:String
    ): Response<Root>
}