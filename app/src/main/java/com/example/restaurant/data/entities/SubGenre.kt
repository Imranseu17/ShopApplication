package com.example.restaurant.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SubGenre (

  @JsonProperty("code" ) var subGenreCode : String? ,
  @JsonProperty("name" ) var subGenreName : String? 

){
  constructor():this("","")

  override fun toString(): String {
    return "SubGenre(subGenreCode=$subGenreCode, subGenreName=$subGenreName)"
  }


}