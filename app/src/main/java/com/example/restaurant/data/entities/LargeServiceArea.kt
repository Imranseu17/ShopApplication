package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize


@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class LargeServiceArea (

  @JsonProperty("code" ) var largeServiceAreaCode : String? ,
  @JsonProperty("name" ) var largeServiceAreaName : String? 

):Parcelable{
  constructor():this("","")

  override fun toString(): String {
    return "LargeServiceArea(largeServiceAreaCode=$largeServiceAreaCode, largeServiceAreaName=$largeServiceAreaName)"
  }


}