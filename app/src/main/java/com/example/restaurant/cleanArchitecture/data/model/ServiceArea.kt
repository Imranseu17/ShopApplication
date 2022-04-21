package com.example.restaurant.cleanArchitecture.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class ServiceArea (

  @JsonProperty("code" ) var serviceAreaCode : String? ,
  @JsonProperty("name" ) var serviceAreaName : String? 

){
  constructor():this("","")

  override fun toString(): String {
    return "ServiceArea(serviceAreaCode=$serviceAreaCode, serviceAreaName=$serviceAreaName)"
  }


}