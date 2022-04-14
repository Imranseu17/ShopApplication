package com.example.restaurant.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class MiddleArea (

  @JsonProperty("code" ) var middleAreaCode : String? ,
  @JsonProperty("name" ) var middleAreaName : String? 

){
  constructor():this("","")

  override fun toString(): String {
    return "MiddleArea(middleAreaCode=$middleAreaCode, middleAreaName=$middleAreaName)"
  }


}