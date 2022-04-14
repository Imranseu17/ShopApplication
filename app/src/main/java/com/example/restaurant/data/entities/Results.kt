package com.example.restaurant.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class Results (

  @JsonProperty("api_version"       ) var api_version       : String?,
  @JsonProperty("results_available" ) var results_available : Int?,
  @JsonProperty("results_returned"  ) var results_returned  : String?,
  @JsonProperty("results_start"     ) var results_start     : Int?,
  @JsonProperty("shop"              ) var shop             : List<Shop>?

){
  constructor():this("",0,"",0, emptyList<Shop>())

  override fun toString(): String {
    return "Results(api_version=$api_version, results_available=$results_available, results_returned=$results_returned, results_start=$results_start, shop=$shop)"
  }


}