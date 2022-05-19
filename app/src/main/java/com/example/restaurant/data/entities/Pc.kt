package com.example.restaurant.data.entities
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Pc (

  @JsonProperty("l" ) var l : String? ,
  @JsonProperty("m" ) var m : String? ,
  @JsonProperty("s" ) var s : String? 

):Parcelable{
  constructor():this("","","")

  override fun toString(): String {
    return "Pc(l=$l, m=$m, s=$s)"
  }


}