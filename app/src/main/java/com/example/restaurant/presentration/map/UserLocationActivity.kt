package com.example.restaurant.presentration.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.restaurant.R
import com.example.restaurant.databinding.ActivityUserLocationBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class UserLocationActivity : AppCompatActivity() ,OnMapReadyCallback{

    private lateinit var binding: ActivityUserLocationBinding

    private val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445

    private var googleMap: GoogleMap? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var currentLocationMarker: Marker? = null
    private var currentLocation: Location? = null
    private var firstTimeFlag = true
    var PROXIMITY_RADIUS = 500


    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (locationResult.lastLocation == null) return
            currentLocation = locationResult.lastLocation
            if (firstTimeFlag && googleMap != null) {
                animateCamera(currentLocation!!)
                firstTimeFlag = false
            }
            showMarker(currentLocation!!)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            1
        )

        val mapMessageLocation = supportFragmentManager.findFragmentById(R.id.map) as   SupportMapFragment
        mapMessageLocation.getMapAsync(this);

        binding?.currentLocationImageButton?.setOnClickListener(View.OnClickListener {
            if(googleMap != null && currentLocation != null){
                animateCamera(currentLocation!!)
            }
        })

        binding?.nearbyrestaurant?.setOnClickListener(View.OnClickListener {
            if(googleMap != null && currentLocation != null){
                val sbValue:StringBuilder =
                    StringBuilder(nearbyRestaurant(currentLocation!!.latitude,currentLocation!!.longitude))
                val placesTask = PlacesTask()
                placesTask.mGoogleMap = googleMap
                placesTask.execute(sbValue.toString())
            }
        })
    }

    private fun loadPermissions(perm: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, arrayOf(perm), requestCode)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap;
    }

    private fun startCurrentLocationUpdates() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(3000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
                return
            }
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (ConnectionResult.SUCCESS == status) return true else {
            if (googleApiAvailability.isUserResolvableError(status)) Toast.makeText(
                this,
                "Please Install google play services to use this application",
                Toast.LENGTH_LONG
            ).show()
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) Toast.makeText(
                this,
                "Permission denied by uses",
                Toast.LENGTH_SHORT
            )
                .show() else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) startCurrentLocationUpdates()
        }
    }

    private fun animateCamera(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
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

    private fun showMarker(currentLocation: Location) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        if (currentLocationMarker == null) currentLocationMarker = googleMap!!.addMarker(
            MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(latLng)
                .title(getCompleteAddressString(currentLocation.latitude,currentLocation.longitude))
        ) else MarkerAnimation.animateMarkerToGB(
            currentLocationMarker!!,
            latLng,
            LatLngInterPolator.Spherical()
        )
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

    override fun onStop() {
        super.onStop()
        if (fusedLocationProviderClient != null) fusedLocationProviderClient!!.removeLocationUpdates(
            mLocationCallback
        )
    }

    override fun onResume() {
        super.onResume()
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            startCurrentLocationUpdates()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient = null
        googleMap = null
    }



    private fun nearbyRestaurant(mLatitude:Double,mLongitude:Double):StringBuilder{
        val sb =
            java.lang.StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        sb.append("location=$mLatitude,$mLongitude")
        sb.append("&radius=5000")
        sb.append("&types=restaurant")
        sb.append("&sensor=true")
        sb.append("&key="+getString(R.string.my_api_key))
        Log.d("Map", "api: $sb")

        return sb
    }
}