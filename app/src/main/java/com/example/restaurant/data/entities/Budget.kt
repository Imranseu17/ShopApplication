package com.example.restaurant.data.entities



import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Budget (

  @JsonProperty("average" ) var average : String? ,
  @JsonProperty("code"    ) var budgetCode    : String? ,
  @JsonProperty("name"    ) var budgetName   : String? 
){
  constructor():this("","","")

  override fun toString(): String {
    return "Budget(average=$average, budgetCode=$budgetCode, budgetName=$budgetName)"
  }


}