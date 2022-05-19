package com.example.restaurant.presentration.restaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.restaurant.data.entities.Shop
import com.example.restaurant.databinding.ActivityRestaurantDetailScreenBinding
import com.example.restaurant.presentration.nearbyPlaces.RestaurantLocationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailScreenBinding
    var lat:Double? = null
    var lng:Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.showLocation.setOnClickListener {
            val intent = Intent(this, RestaurantLocationActivity::class.java)
            intent.putExtra("lat",lat)
            intent.putExtra("lng",lng)
            startActivity(intent)
        }

        val data:Shop = intent.getParcelableExtra("shop")!!
        bindCharacter(data)
        binding.backButton.setOnClickListener { finish() }
    }

    private fun bindCharacter(shop: Shop) {
        lat = shop.lat
        lng = shop.lng
        binding.name.text = "Name: "+shop.name
        binding.accessText.text = "Access: "+ shop.access
        binding.address.text = "Address: "+ shop.address
        binding.band.text = "Band: "+ shop.band
        binding.barrierFree.text = "Barrier Free: "+shop.barrierFree
        binding.budgetAverage.text = "Budget Average: "+shop.budget?.average
        binding.budgetCode.text = "Budget Code: "+shop.budget?.budgetCode
        binding.budgetName.text = "Budget Name: "+shop.budget?.budgetName
        binding.budgetMemo.text = "Budget Memo: "+shop.budgetMemo
        binding.capacity.text = "Capacity: "+shop.capacity
        binding.card.text = "Card: "+shop.card
        binding.catchText.text = "Catch: "+shop.catch
        binding.charter.text = "Charter: "+shop.charter
        binding.child.text = "Child: "+shop.child
        binding.close.text = "Close: "+shop.close
        binding.course.text = "Course: "+shop.course
        binding.english.text = "English: "+shop.english
        binding.freeDrink.text = "Free Drink: "+shop.freeDrink
        binding.freeFood.text = "Free Food: "+shop.freeFood
        binding.genreCatch.text = "Genre Catch: "+shop.genre?.catch
        binding.genreCode.text = "Genre Code: "+shop.genre?.genreCode
        binding.genreName.text = "Genre Name: "+shop.genre?.genreName
        binding.horigotatsu.text = "Horigotatsu: "+shop.horigotatsu
        binding.karaoke.text ="Karaoke: "+ shop.karaoke
        binding.ktaiCoupon.text = "ktaiCoupon"+shop.ktaiCoupon
        binding.largeAreaCode.text = "Large Area Code: "+shop.largeArea?.largeAreaCode
        binding.largeAreaName.text = "Large Area Name: "+shop.largeArea?.largeAreaName
        binding.largeServiceCode.text = "Large Service Area Code: "+shop.largeServiceArea?.largeServiceAreaCode
        binding.largeServiceName.text = "Large Service Area Name: "+shop.largeServiceArea?.largeServiceAreaName
        binding.lat.text = "Lat: "+shop.lat
        binding.lng.text = "Lng: "+shop.lng
        binding.lunch.text = "Lunch: "+shop.lunch
        binding.middleAreaCode.text = "Middle Area Code: "+shop.middleArea?.middleAreaCode
        binding.middleAreaName.text = "Middle Area Name: "+shop.middleArea?.middleAreaName
        binding.midnight.text = "Midnight: "+shop.midnight
        binding.mobileAccess.text = "MobileAccess: "+shop.mobileAccess
        binding.nameKana.text = "NameKana: "+shop.nameKana
        binding.nonSmoking.text = "NonSmoking: "+shop.nonSmoking
        binding.open.text = "open: "+shop.open
        binding.otherMemo.text = "OtherMemo: "+shop.otherMemo
        binding.parking.text = "Parking: "+shop.parking
        binding.partyCapacity.text = "Party Capacity: "+shop.partyCapacity
        binding.pet.text = "Pet: "+shop.pet
        binding.privateRoom.text = "PrivateRoom: "+shop.privateRoom
        binding.serviceAreaCode.text = "Service Area Code: "+shop.serviceArea?.serviceAreaCode
        binding.serviceAreaName.text = "Service Area Name: "+shop.serviceArea?.serviceAreaName
        binding.shopDetailsMemo.text = "ShopDetailMemo: "+shop.shopDetailMemo
        binding.show.text = "Show: "+shop.show
        binding.smallAreaCode.text = "Small Area Code: "+shop.smallArea?.smallAreaCode
        binding.smallAreaName.text = "Small Area Name: "+shop.smallArea?.smallAreaName
        binding.stationName.text = "StationName: "+shop.stationName
        binding.subGenreCode.text = "Sub Genre Code: "+shop.subGenre?.subGenreCode
        binding.subGenreName.text = "Sub Genre Name: "+shop.subGenre?.subGenreName
        binding.tatami.text = "Tatami: "+shop.tatami
        binding.tv.text = "Tv: "+shop.tv
        binding.wedding.text = "Wedding: "+shop.wedding
        binding.wifi.text = "Wifi: "+shop.wifi

        Glide.with(binding.root)
            .load(shop.photo?.mobile?.longSize)
            .into(binding.photo)


    }
}