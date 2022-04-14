package com.example.restaurant.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class SmallArea (

  @JsonProperty("code" ) var smallAreaCode : String? ,
  @JsonProperty("name" ) var smallAreaName : String? 

){
  constructor():this("","")

  override fun toString(): String {
    return "SmallArea(smallAreaCode=$smallAreaCode, smallAreaName=$smallAreaName)"
  }


}