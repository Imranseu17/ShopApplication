package com.example.restaurant.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class LargeServiceArea (

  @JsonProperty("code" ) var largeServiceAreaCode : String? ,
  @JsonProperty("name" ) var largeServiceAreaName : String? 

){
  constructor():this("","")

  override fun toString(): String {
    return "LargeServiceArea(largeServiceAreaCode=$largeServiceAreaCode, largeServiceAreaName=$largeServiceAreaName)"
  }


}