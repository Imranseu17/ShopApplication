package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize


@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class MiddleArea (

  @JsonProperty("code" ) var middleAreaCode : String? ,
  @JsonProperty("name" ) var middleAreaName : String? 

):Parcelable{
  constructor():this("","")

  override fun toString(): String {
    return "MiddleArea(middleAreaCode=$middleAreaCode, middleAreaName=$middleAreaName)"
  }


}