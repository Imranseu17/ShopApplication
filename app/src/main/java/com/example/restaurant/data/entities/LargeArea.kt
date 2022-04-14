package com.example.restaurant.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LargeArea (

  @JsonProperty("code" ) var largeAreaCode : String? ,
  @JsonProperty("name" ) var largeAreaName : String? 

)
{
  constructor():this("","")

  override fun toString(): String {
    return "LargeArea(largeAreaCode=$largeAreaCode, largeAreaName=$largeAreaName)"
  }


}