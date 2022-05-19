package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class SubGenre (

  @JsonProperty("code" ) var subGenreCode : String? ,
  @JsonProperty("name" ) var subGenreName : String? 

):Parcelable{
  constructor():this("","")

  override fun toString(): String {
    return "SubGenre(subGenreCode=$subGenreCode, subGenreName=$subGenreName)"
  }


}