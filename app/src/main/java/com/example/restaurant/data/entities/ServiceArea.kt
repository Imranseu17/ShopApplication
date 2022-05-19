package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class ServiceArea (

  @JsonProperty("code" ) var serviceAreaCode : String? ,
  @JsonProperty("name" ) var serviceAreaName : String? 

):Parcelable{
  constructor():this("","")

  override fun toString(): String {
    return "ServiceArea(serviceAreaCode=$serviceAreaCode, serviceAreaName=$serviceAreaName)"
  }


}