package com.example.restaurant.cleanArchitecture.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Root (

  @JsonProperty("results" ) var results : Results? = Results()

){
  constructor():this(Results())

  override fun toString(): String {
    return "Root(results=$results)"
  }


}