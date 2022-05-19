package com.example.restaurant.data.entities

import android.os.Parcelable
import androidx.room.Embedded
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Photo (
  @Embedded
  @JsonProperty("mobile" ) var mobile : Mobile? = Mobile(),
  @Embedded
  @JsonProperty("pc"     ) var pcPhoto     : Pc?     = Pc()

):Parcelable{
  constructor():this(Mobile(),Pc())

  override fun toString(): String {
    return "Photo(mobile=$mobile, pcPhoto=$pcPhoto)"
  }


}