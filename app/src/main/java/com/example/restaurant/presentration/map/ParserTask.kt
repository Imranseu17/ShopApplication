package com.example.restaurant.presentration.map

import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject

class ParserTask : AsyncTask<String?, Int?, List<HashMap<String, String>>?>() {
    var jObject: JSONObject? = null
    var  mGoogleMap: GoogleMap? = null

    // Invoked by execute() method of this object
     override fun doInBackground(vararg jsonData: String?): List<HashMap<String, String>>? {
        var places: List<HashMap<String, String>>? = null
        val placeJson = Place_JSON()

        try {
            jObject = JSONObject(jsonData[0])
            places = placeJson.parse(jObject!!)
        } catch (e: Exception) {
            Log.d("Exception", e.toString())
        }
        return places
    }

    // Executed after the complete execution of doInBackground() method
    override fun onPostExecute(list: List<HashMap<String, String>>?) {
        Log.d("Map", "list size: " + list!!.size)
        // Clears all the existing markers;
        mGoogleMap?.clear()
        for (i in list.indices) {

            // Creating a marker
            val markerOptions = MarkerOptions()

            // Getting a place from the places list
            val hmPlace = list[i]


            // Getting latitude of the place
            val lat = hmPlace["lat"]!!.toDouble()

            // Getting longitude of the place
            val lng = hmPlace["lng"]!!.toDouble()

            // Getting name
            val name = hmPlace["place_name"]
            Log.d("Map", "place: $name")

            // Getting vicinity
            val vicinity = hmPlace["vicinity"]
            val latLng = LatLng(lat, lng)

            // Setting the position for the marker
            markerOptions.position(latLng)
            markerOptions.title("$name : $vicinity")
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

            // Placing a marker on the touched position
            val m: Marker = mGoogleMap!!.addMarker(markerOptions)
        }
    }
}