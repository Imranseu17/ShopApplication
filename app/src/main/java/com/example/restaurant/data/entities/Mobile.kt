package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize


@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Mobile (

  @JsonProperty("l" ) var longSize : String? ,
  @JsonProperty("s" ) var shortSize : String? 

):Parcelable{
  constructor():this("","")

  override fun toString(): String {
    return "Mobile(longSize=$longSize, shortSize=$shortSize)"
  }


}