package com.example.restaurant.domain.services


import android.os.Build
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class APIClient {
    private var retrofit: Retrofit? = null
    var BASE_URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/"

    @Synchronized
    fun getAPI(): APIService? {
        retrofit = Retrofit. Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        return  retrofit?.create(APIService::class.java)
    }



}