package com.example.restaurant.presentration.restaurants


import android.content.Context
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
import com.squareup.picasso.Picasso
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
    var homeplace: Shop? = null
    var latlngs: ArrayList<LatLng> = ArrayList()

    val PATTERN_DASH_LENGTH_PX = 20
    val PATTERN_GAP_LENGTH_PX = 20
    val DOT: PatternItem = Dot()
    val DASH: PatternItem = Dash(PATTERN_DASH_LENGTH_PX.toFloat())
    val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    val PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DOT)
    var massage:ArrayList<String> = ArrayList()



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
       // googleMap.setOnMarkerClickListener(this)
        setResult()
        binding.currentLocationImageButton.
        setOnClickListener {
            animateCamera(homeplace?.lat!!,homeplace?.lng!!)
        }

  //      googleMap?.setOnMarkerClickListener(this);
//        binding.currentLocationImageButton.setOnClickListener{
//            this.googleMap!!.addMarker(MarkerOptions().position(
//                LatLng(35.669220,
//                    139.761457)).title(getCompleteAddressString(35.669220,
//                139.761457)).icon(BitmapDescriptorFactory.
//            defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
//        }


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
                        googleMap?.clear()
                        Log.e("size: ",""+it.data.size)
                        list = it.data as ArrayList<Shop>
                        adapter.setItems(it.data)
                        val size = it.data.size-1
                        for (i in 0..size) {
                            // Getting a place from the places list
                            homeplace = it.data.get(i)
                            animateCamera(homeplace!!.lat!!, homeplace!!.lng!!)
                            // Getting vicinity
                            latLng = LatLng(homeplace!!.lat!!, homeplace!!.lng!!)

                            val marker = googleMap?.addMarker(MarkerOptions().
                            position(latLng).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)))
                            marker?.tag = it.data.get(i)
                            var latLng = LatLng(it.data.get(0).lat!!, it.data.get((0)).lng!!)
                            googleMap?.animateCamera(CameraUpdateFactory.
                            newLatLngZoom(latLng, 11f))
                            if(it.data.size !=0){

                               val testInfoWindowAdapter = TestInfoWindowAdapter(this)
                                googleMap?.setInfoWindowAdapter(testInfoWindowAdapter)
                            }
                            // Setting the position for the marker

                            latlngs.add(latLng)
                            massage.add(homeplace?.address!!)
                        }

                        val rectOptions = PolylineOptions().addAll(latlngs).color(Color.RED)
                        rectOptions.pattern(PATTERN_POLYGON_ALPHA)
                        googleMap!!.addPolyline(rectOptions)
                        drawCircle(latLng)
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
        circleOptions.radius(40.0)
        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK)
        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000)
        // Border width of the circle
        circleOptions.strokeWidth(2f)
        circleOptions.strokePattern(Collections.singletonList(Dot()) as List<PatternItem>?)
        circleOptions.zIndex(1f)

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

class TestInfoWindowAdapter : GoogleMap.InfoWindowAdapter{
    private lateinit var context:Context

    constructor(context: Context) {
        this.context = context
    }

    override fun getInfoWindow(args: Marker?): View? {
        return null
    }

    override fun getInfoContents(p0: Marker?): View? {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.markerlayout, null)
       val text = view.findViewById(R.id.address_location) as TextView
       val imageview = view.findViewById(R.id.image_shop) as  ImageView

        val shop:Shop = p0?.tag as Shop
        val image_URL = shop.photo?.mobile?.longSize
        Log.e("message: ", text.text.toString())
        text.text = shop.address
            Picasso.get().load(image_URL).
        into(imageview,MarkerCallBack(p0))

        return view
    }

    class MarkerCallBack: com.squareup.picasso.Callback{
       var marker:Marker

        constructor(marker: Marker) {
            this.marker = marker
        }

        override fun onSuccess() {
            if(marker != null && marker.isInfoWindowShown()){
                marker.hideInfoWindow()
                marker.showInfoWindow()
            }
        }

        override fun onError(e: java.lang.Exception?) {
            TODO("Not yet implemented")
        }


    }


}







