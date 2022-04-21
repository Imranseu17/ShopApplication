package com.example.restaurant.cleanArchitecture.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CouponUrls (

  @JsonProperty("pc" ) var couponUrlsPc : String? ,
  @JsonProperty("sp" ) var sp : String? 
) {
  constructor():this("","")

  override fun toString(): String {
    return "CouponUrls(couponUrlsPc=$couponUrlsPc, sp=$sp)"
  }


}