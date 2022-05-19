package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize


@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class SmallArea (

  @JsonProperty("code" ) var smallAreaCode : String? ,
  @JsonProperty("name" ) var smallAreaName : String? 

):Parcelable{
  constructor():this("","")

  override fun toString(): String {
    return "SmallArea(smallAreaCode=$smallAreaCode, smallAreaName=$smallAreaName)"
  }


}