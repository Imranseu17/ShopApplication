package com.example.restaurant.domain.presenter

import android.util.Log
import com.example.restaurant.data.entities.Results
import com.example.restaurant.data.entities.Root
import com.example.restaurant.domain.callback.RestaurantSearchApI
import com.example.restaurant.domain.enumClass.ErrorCode
import com.example.restaurant.domain.enumClass.SearchByKeywordEnum
import com.example.restaurant.domain.errors.ApiError
import com.example.restaurant.domain.services.APIClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class RestaurantSearchPresenter(view: RestaurantSearchApI?) {
    private var mViewInterface: RestaurantSearchApI? = null
    private var mApiClient: APIClient? = null

   init {
        mViewInterface = view
        if (mApiClient == null) {
            mApiClient = APIClient()
        }
    }

    fun attemptSearchByKeyword(
       key:String,keyword:String,count:Int,format:String
    ) {
        mApiClient?.getAPI()?.getDataByKeyword(key, keyword, count, format)?.
        enqueue(object : Callback<Root?> {
            override fun onResponse(call: Call<Root?>, response: Response<Root?>) {
                Log.e("response code: ",response.code().toString())
                if (response.isSuccessful()) {
                    val results: Root? = response.body()
                    Log.e("response body: ",response.body().toString())
                    if (results != null) {
                        mViewInterface?.onSuccess(results, response.code())
                    }
                } else getErrorMessage(response.code(), response.errorBody())
            }

            override fun onFailure(call: Call<Root?>, e: Throwable) {
                e.printStackTrace()
                if (e is HttpException) {
                    val code = e.response()!!.code()
                    val responseBody = e.response()!!.errorBody()
                    getErrorMessage(code, responseBody)
                } else if (e is SocketTimeoutException) {
                    mViewInterface?.onError(
                        "Server connection error",
                        SearchByKeywordEnum.SearchByKeyword_FAILED.code
                    )
                } else if (e is IOException) {
                    if (e.message != null) mViewInterface?.onError(
                        e.message,
                        SearchByKeywordEnum.SearchByKeyword_FAILED.code
                    ) else mViewInterface?.onError("IO Exception",
                        SearchByKeywordEnum.SearchByKeyword_FAILED.code)
                } else {
                    mViewInterface?.onError(
                        "Unknown exception: " + e.message,
                        SearchByKeywordEnum.SearchByKeyword_FAILED.code
                    )
                    e.printStackTrace()
                }
            }
        })
    }

    private fun getErrorMessage(code: Int, responseBody: ResponseBody?) {
        val errorCode: ErrorCode = ErrorCode.getByCode(code)
        if (errorCode != null) {
            when (errorCode) {
                ErrorCode.ERRORCODE500 -> mViewInterface?.onError(
                    ApiError.get500ErrorMessage(responseBody),
                    SearchByKeywordEnum.ERROR_CODE_100.getCode()
                )
                ErrorCode.ERRORCODE400 -> mViewInterface?.onError(
                    ApiError.get500ErrorMessage(responseBody),
                    SearchByKeywordEnum.ERROR_CODE_100.getCode()
                )
                ErrorCode.ERRORCODE406 -> mViewInterface?.onError(
                    ApiError.get406ErrorMessage(responseBody),
                    SearchByKeywordEnum.ERROR_CODE_100.getCode()
                )
                ErrorCode.SERVER_ERROR_CODE -> mViewInterface?.onError(
                    ApiError.getErrorMessage(responseBody),
                    SearchByKeywordEnum.ERROR_CODE_100.getCode()
                )
                else -> mViewInterface?.onError(
                    ApiError.getErrorMessage(responseBody),
                    SearchByKeywordEnum.ERROR_CODE_100.getCode()
                )
            }
        } else {
            mViewInterface?.onError("Error occurred Please try again", code)
        }
    }
}