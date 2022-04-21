package com.example.restaurant.cleanArchitecture.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Urls (

  @JsonProperty("pc" ) var urlsPc : String? 

){
  constructor():this("")

  override fun toString(): String {
    return "Urls(urlsPc=$urlsPc)"
  }


}