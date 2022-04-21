package com.example.restaurant.cleanArchitecture.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Genre (

  @JsonProperty("catch" ) var catch : String? ,
  @JsonProperty("code"  ) var genreCode  : String? ,
  @JsonProperty("name"  ) var genreName  : String? 

){
  constructor():this("","","")

  override fun toString(): String {
    return "Genre(catch=$catch, genreCode=$genreCode, genreName=$genreName)"
  }


}