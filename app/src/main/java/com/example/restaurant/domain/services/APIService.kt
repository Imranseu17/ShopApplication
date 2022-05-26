package com.example.restaurant.domain.services

import com.example.restaurant.data.entities.Results
import com.example.restaurant.data.entities.Root
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
    fun getDataByKeyword(
        @Query("key") key:String,
        @Query("keyword") keyword:String,
        @Query("count") count:Int,
        @Query("format")format:String
    ): Call<Root?>?

}