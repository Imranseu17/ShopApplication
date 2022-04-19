package com.example.restaurant.presentration

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurant.R
import com.example.restaurant.databinding.ActivityRestaurantLocationBinding
import com.example.restaurant.databinding.ShopDetailFragmentBinding
import com.example.restaurant.usecase.autoCleared
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class RestaurantLocationActivity : AppCompatActivity(),OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val zoomLevel = 16.0f
        mMap = googleMap
        val lat = intent.getDoubleExtra("lat",0.00)
        val lng = intent.getDoubleExtra("lng",0.00)
        findViewById<ImageButton>(R.id.currentLocationImageButton).setOnClickListener {
            if(googleMap != null && lat != null && lng != null){
                animateCamera(lat,lng)
            }
        }
        val latLng = LatLng(lat,lng)
        mMap!!.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(getCompleteAddressString(lat,lng))
        )
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel))
    }

    private fun animateCamera(lat:Double,lng:Double) {
        val latLng = LatLng(lat,lng)
        mMap!!.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                getCameraPositionWithBearing(
                    latLng
                )
            )
        )
    }

    private fun getCameraPositionWithBearing(latLng: LatLng): CameraPosition {
        return CameraPosition.Builder().target(latLng).zoom(16f).build()
    }


    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double):
            String? {
        var strAdd = ""
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.getMaxAddressLineIndex()) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.d("Restaurant Location", strReturnedAddress.toString())
            } else {
                Log.d("Restaurant Location", "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Restaurant Location", "Canont get Address!")
        }
        return strAdd
    }
}