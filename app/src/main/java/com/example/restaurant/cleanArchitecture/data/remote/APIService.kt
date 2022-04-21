package com.example.restaurant.cleanArchitecture.data.remote

import com.example.restaurant.cleanArchitecture.data.model.Root
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.*

interface APIService {
    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
     fun getAllShops(@Query("key") key:String,
                            @Query("large_area") large_area:String,
                            @Query("format")format:String)
            : Observable<Root>

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("v1")
     fun getShop(
        @Query("key") key:String,
        @Query("id") id:String,
        @Query("format")format:String
    ): Observable<Root>
}