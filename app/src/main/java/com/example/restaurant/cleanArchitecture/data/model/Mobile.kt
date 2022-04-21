package com.example.restaurant.cleanArchitecture.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class Mobile (

  @JsonProperty("l" ) var longSize : String? ,
  @JsonProperty("s" ) var shortSize : String? 

){
  constructor():this("","")

  override fun toString(): String {
    return "Mobile(longSize=$longSize, shortSize=$shortSize)"
  }


}