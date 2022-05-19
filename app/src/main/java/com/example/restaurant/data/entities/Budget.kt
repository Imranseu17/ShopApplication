package com.example.restaurant.data.entities



import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Budget (

  @JsonProperty("average" ) var average : String? ,
  @JsonProperty("code"    ) var budgetCode    : String? ,
  @JsonProperty("name"    ) var budgetName   : String? 
):Parcelable{
  constructor():this("","","")

  override fun toString(): String {
    return "Budget(average=$average, budgetCode=$budgetCode, budgetName=$budgetName)"
  }


}