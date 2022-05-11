package com.example.restaurant.presentration

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurant.R
import com.example.restaurant.databinding.ActivityRestaurantLocationBinding
import com.example.restaurant.presentration.nearbyPlaces.NearbyPlacesViewModel
import com.example.restaurant.usecase.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class RestaurantLocationActivity : AppCompatActivity(),OnMapReadyCallback ,GoogleMap.OnMarkerClickListener{
    private var mMap: GoogleMap? = null
    private var myMarker: Marker? = null
    private val viewModel: NearbyPlacesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_location)
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
                val latLng = LatLng(lat,lng)
                mMap!!.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(getCompleteAddressString(lat,lng)))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))

            }
        }
        findViewById<ImageButton>(R.id.nearbyrestaurant).setOnClickListener{
            if(googleMap != null && lat != null && lng != null){
                setResult()
            }
        }
        val latLng = LatLng(lat,lng)
      myMarker =   mMap!!.addMarker(
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

    private fun setResult(){

        viewModel.nearbyPlacesSearch.observe(this, androidx.lifecycle.Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()){
                        mMap?.clear()
                        for (i in 1 .. it.data.size){
                            val markerOptions = MarkerOptions()

                            // Getting a place from the places list
                            val hmPlace = it.data.get(i-1)


                            // Getting latitude of the place
                            val lat = hmPlace.lat

                            // Getting longitude of the place
                            val lng = hmPlace.lng



                            // Getting vicinity
                            val latLng = LatLng(lat!!, lng!!)

                            // Setting the position for the marker
                            markerOptions.position(latLng)
                            markerOptions.title(getCompleteAddressString(lat,lng))
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

                            // Placing a marker on the touched position
                            mMap!!.addMarker(markerOptions)
                        }
                    }
                }
                Resource.Status.ERROR ->{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker!!.equals(myMarker))
        {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            return true
        }
        return false
    }


}