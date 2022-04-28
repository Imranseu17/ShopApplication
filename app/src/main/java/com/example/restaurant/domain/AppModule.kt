package com.example.restaurant.domain

import android.content.Context
import com.example.restaurant.data.local.ShopDao
import com.example.restaurant.data.remote.ShopRemoteDataSource
import com.example.restaurant.data.remote.ShopService
import com.example.restaurant.data.repository.ShopRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    var BASE_URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/"

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient()
        .create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): ShopService = retrofit.create(ShopService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(shopService: ShopService) = ShopRemoteDataSource(shopService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        com.example.restaurant.data.local.AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: com.example.restaurant.data.local.AppDatabase) = db.shopDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ShopRemoteDataSource,
                          localDataSource: ShopDao) =
        ShopRepository(remoteDataSource, localDataSource)




}





