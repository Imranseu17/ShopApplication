package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class LargeArea (

  @JsonProperty("code" ) var largeAreaCode : String? ,
  @JsonProperty("name" ) var largeAreaName : String? 

):Parcelable
{
  constructor():this("","")

  override fun toString(): String {
    return "LargeArea(largeAreaCode=$largeAreaCode, largeAreaName=$largeAreaName)"
  }


}