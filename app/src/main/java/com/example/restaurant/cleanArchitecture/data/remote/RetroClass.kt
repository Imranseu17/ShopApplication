package com.example.restaurant.cleanArchitecture.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetroClass {
    companion object {
        const val BASE_URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/"

        fun getRetroInstance():Retrofit{

            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder().
            addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()
        }

        fun getAPIService(): APIService {
            return getRetroInstance().create(APIService::class.java)
        }

    }


}