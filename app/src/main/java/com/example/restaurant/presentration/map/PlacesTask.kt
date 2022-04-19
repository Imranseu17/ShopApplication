package com.example.restaurant.presentration.map

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.Throws

class PlacesTask : AsyncTask<String?, Int?, String?>() {
    var data: String? = null

    // Invoked by execute() method of this object
     override fun doInBackground(vararg url: String?): String? {
        try {
            data = downloadUrl(url[0]!!)
        } catch (e: Exception) {
            Log.d("Background Task", e.toString())
        }
        return data
    }

    // Executed after the complete execution of doInBackground() method
    override fun onPostExecute(result: String?) {
        val parserTask = ParserTask()

        // Start parsing the Google places in JSON format
        // Invokes the "doInBackground()" method of the class ParserTask
        parserTask.execute(result)
    }

    @Throws(IOException::class)
    private fun downloadUrl(strUrl: String): String {
        var data = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL(strUrl)

            // Creating an http connection to communicate with url
            urlConnection = url.openConnection() as HttpURLConnection

            // Connecting to url
            urlConnection.connect()

            // Reading data from url
            iStream = urlConnection!!.inputStream
            val br = BufferedReader(InputStreamReader(iStream))
            val sb = StringBuffer()
            var line: String? = ""
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }
            data = sb.toString()
            br.close()
        } catch (e: Exception) {
            Log.d("downloading url", e.toString())
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return data
    }


}