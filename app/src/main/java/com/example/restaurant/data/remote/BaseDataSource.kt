package com.example.restaurant.data.remote

import android.util.Log
import com.example.restaurant.usecase.Resource
import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                Log.e("Data: ",""+body)
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }


    private fun <T> error(message: String): Resource<T> {
        Timber.d(message)
        Log.e("error: ",message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

}