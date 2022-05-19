package com.example.restaurant.presentration.restaurants


import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurant.R
import com.example.restaurant.data.entities.Shop
import com.example.restaurant.databinding.ActivityRestaurantLocationsBinding
import com.example.restaurant.presentration.ShopListAdapter
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
class RestaurantLocations : AppCompatActivity() ,OnMapReadyCallback{

    private lateinit var binding: ActivityRestaurantLocationsBinding
    private var googleMap: GoogleMap? = null
    private val viewModel: NearbyPlacesViewModel by viewModels()
    private lateinit var adapter: ShopListAdapter
    private  var list = ArrayList<Shop>()
    private lateinit var latLng:LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantLocationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapMessageLocation = supportFragmentManager.findFragmentById(R.id.map)
                as  SupportMapFragment
        mapMessageLocation.getMapAsync(this);
        setupRecyclerView()
        binding.zoomIncrease.setOnClickListener {
            googleMap!!.animateCamera(CameraUpdateFactory.zoomIn())
        }

        binding.zoomDecrease.setOnClickListener {
            googleMap!!.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap;
        setResult()
        binding.currentLocationImageButton.setOnClickListener{
            this.googleMap!!.addMarker(MarkerOptions().position(
                LatLng(35.669220,
                    139.761457)).title(getCompleteAddressString(35.669220,
                139.761457)).icon(BitmapDescriptorFactory.
            defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
        }

    }

    private fun animateCamera(lat:Double,lng:Double) {
        val latLng = LatLng(lat,lng)
        googleMap!!.animateCamera(
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

    private fun setupRecyclerView() {
        adapter = ShopListAdapter(this,list)
        binding.shopRvCoordinates.layoutManager = LinearLayoutManager(this)
        binding.shopRvCoordinates.adapter = adapter

    }


    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String? {
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
                Log.d("Current Location", strReturnedAddress.toString())
            } else {
                Log.d("Current Location", "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Current Location", "Canont get Address!")
        }
        return strAdd
    }

    private fun setResult(){

        viewModel.nearbyPlacesSearch.observe(this,
            androidx.lifecycle.Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.shimmerViewContainer.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()){
                        list = it.data as ArrayList<Shop>
                        adapter.setItems(it.data)

                    }
                    if (!it.data.isNullOrEmpty()){
                        googleMap?.clear()
                        for (i in 1 .. it.data.size){
                            val markerOptions = MarkerOptions()

                            // Getting a place from the places list
                            val hmPlace = it.data.get(i-1)


                            // Getting latitude of the place
                            val lat = hmPlace.lat

                            // Getting longitude of the place
                            val lng = hmPlace.lng

                            animateCamera(lat!!,lng!!)

                            // Getting vicinity
                            latLng = LatLng(lat, lng)



                            // Setting the position for the marker
                            markerOptions.position(latLng)
                            markerOptions.title(getCompleteAddressString(hmPlace.lat!!,hmPlace.lng!!))
                            markerOptions.icon(BitmapDescriptorFactory.
                            defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

                            // Placing a marker on the touched position
                            googleMap!!.addMarker(markerOptions)
                            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,19.65f));

                        }

                        drawCircle(latLng)
                      //  googleMap!!.getUiSettings().setZoomControlsEnabled(true);


                    }
                }
                Resource.Status.ERROR ->{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.shimmerViewContainer.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun drawCircle(point: LatLng) {

        // Instantiating CircleOptions to draw a circle around the marker
        val circleOptions = CircleOptions()
        // Specifying the center of the circle
        circleOptions.center(point)
        // Radius of the circle
        circleOptions.radius(34.0)
        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK)
        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000)
        // Border width of the circle
        circleOptions.strokeWidth(2f)
        // Adding the circle to the GoogleMap
        googleMap!!.addCircle(circleOptions)
    }



    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

}


