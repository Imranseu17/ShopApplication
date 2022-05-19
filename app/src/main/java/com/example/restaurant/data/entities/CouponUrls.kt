package com.example.restaurant.data.entities

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class CouponUrls (

  @JsonProperty("pc" ) var couponUrlsPc : String? ,
  @JsonProperty("sp" ) var sp : String? 
):Parcelable {
  constructor():this("","")

  override fun toString(): String {
    return "CouponUrls(couponUrlsPc=$couponUrlsPc, sp=$sp)"
  }


}