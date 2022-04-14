package com.example.restaurant.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurant.data.entities.Shop


@Dao
interface ShopDao {

    @Query("SELECT * FROM shop")
    fun getAllShops() : LiveData<List<Shop>>

    @Query("SELECT * FROM shop WHERE id = :id")
    fun getShop(id: String): LiveData<Shop>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(shop: List<Shop>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(shop: List<Shop>?)


}