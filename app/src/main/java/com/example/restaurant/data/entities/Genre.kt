package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Genre (

  @JsonProperty("catch" ) var catch : String? ,
  @JsonProperty("code"  ) var genreCode  : String? ,
  @JsonProperty("name"  ) var genreName  : String? 

):Parcelable{
  constructor():this("","","")

  override fun toString(): String {
    return "Genre(catch=$catch, genreCode=$genreCode, genreName=$genreName)"
  }


}