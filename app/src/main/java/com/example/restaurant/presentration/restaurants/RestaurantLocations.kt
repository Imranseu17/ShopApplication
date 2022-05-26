package com.example.restaurant.presentration.restaurants





import android.app.Dialog
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
    var homeplace: Shop? = null
    var latlngs: ArrayList<LatLng> = ArrayList()

    val PATTERN_DASH_LENGTH_PX = 20
    val PATTERN_GAP_LENGTH_PX = 20
    val DOT: PatternItem = Dot()
    val DASH: PatternItem = Dash(PATTERN_DASH_LENGTH_PX.toFloat())
    val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    val PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DOT)
    private var text:TextView? = null
    private var imageview:ImageView?= null
    private var dialog:Dialog? = null
    private val markers = arrayListOf<Marker>()


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
                        for (i in 0..size){
                            // Getting a place from the places list
                            homeplace = it.data.get(i)
                            animateCamera(homeplace!!.lat!!,homeplace!!.lng!!)
                            // Getting vicinity
                            latLng = LatLng(homeplace!!.lat!!,homeplace!!.lng!!)
                            dialog = Dialog(this)
                            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog?.setCancelable(true)
                            dialog?.setContentView(R.layout.markerlayout)
                            text = dialog?.findViewById(R.id.address_location)
                            imageview = dialog?.findViewById(R.id.image_shop)
                            text?.text = homeplace!!.address
                            Log.e("message: ",text?.text.toString())
                            Glide.with(this)
                                .load(homeplace!!.photo?.mobile?.longSize)
                                .into(imageview!!)
                            // Setting the position for the marker
                            latlngs.add(latLng)
                            markers.add(
                                createMarker(homeplace!!.lat!!,homeplace!!.lng!!,
                                    homeplace!!.address!!,homeplace!!.name)!!
                            )


                        }


                        val rectOptions = PolylineOptions().addAll(latlngs).color(Color.RED)
                        rectOptions.pattern(PATTERN_POLYGON_ALPHA)
//                        googleMap!!.addPolyline(rectOptions)
//                        drawCircle(latLng)
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

//    override fun onMarkerClick(p0: Marker?): Boolean {
//       dialog?.show()
//        return true
//    }

    protected fun createMarker(
        latitude: Double,
        longitude: Double,
        title: String?,
        snippet: String?,
    ): Marker? {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 9f))
     return googleMap?.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.
            defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        )

    }


}


