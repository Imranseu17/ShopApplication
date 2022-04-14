package com.example.restaurant.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "shop")
data class Shop(
  @JsonProperty("access"             ) var access           : String?           ,
  @JsonProperty("address"            ) var address          : String?           ,
  @JsonProperty("band"               ) var band             : String?           ,
  @JsonProperty("barrier_free"       ) var barrierFree      : String?           ,
  
  @Embedded
  @JsonProperty("budget"             ) var budget           : Budget?           ,
  @JsonProperty("budget_memo"        ) var budgetMemo       : String?           ,
  @JsonProperty("capacity"           ) var capacity         : Int?              ,
  @JsonProperty("card"               ) var card             : String?           ,
  @JsonProperty("catch"              ) var catch            : String?           ,
  @JsonProperty("charter"            ) var charter          : String?           ,
  @JsonProperty("child"              ) var child            : String?           ,
  @JsonProperty("close"              ) var close            : String?           ,
  
  @Embedded
  @JsonProperty("coupon_urls"        ) var couponUrls       : CouponUrls?       ,
  @JsonProperty("course"             ) var course           : String?           ,
  @JsonProperty("english"            ) var english          : String?           ,
  @JsonProperty("free_drink"         ) var freeDrink        : String?           ,
  @JsonProperty("free_food"          ) var freeFood         : String?           ,
  
  @Embedded
  @JsonProperty("genre"              ) var genre            : Genre?            ,
  @JsonProperty("horigotatsu"        ) var horigotatsu      : String?           ,


  @PrimaryKey()
  @JsonProperty("id"                 ) var id               : String           ="",
  @JsonProperty("karaoke"            ) var karaoke          : String?           ,
  @JsonProperty("ktai_coupon"        ) var ktaiCoupon       : Int?              ,
  
  @Embedded
  @JsonProperty("large_area"         ) var largeArea        : LargeArea?        ,
  
  @Embedded
  @JsonProperty("large_service_area" ) var largeServiceArea : LargeServiceArea? ,
  @JsonProperty("lat"                ) var lat              : Double?           ,
  @JsonProperty("lng"                ) var lng              : Double?           ,
  @JsonProperty("logo_image"         ) var logoImage        : String?           ,
  @JsonProperty("lunch"              ) var lunch            : String?           ,
  
  @Embedded
  @JsonProperty("middle_area"        ) var middleArea       : MiddleArea?       ,
  @JsonProperty("midnight"           ) var midnight         : String?           ,
  @JsonProperty("mobile_access"      ) var mobileAccess     : String?           ,
  @JsonProperty("name"               ) var name             : String?           ,
  @JsonProperty("name_kana"          ) var nameKana         : String?           ,
  @JsonProperty("non_smoking"        ) var nonSmoking       : String?           ,
  @JsonProperty("open"               ) var open             : String?           ,
  @JsonProperty("other_memo"         ) var otherMemo        : String?           ,
  @JsonProperty("parking"            ) var parking          : String?           ,
  @JsonProperty("party_capacity"     ) var partyCapacity    : Int?              ,
  @JsonProperty("pet"                ) var pet              : String?           ,
  @Embedded
  @JsonProperty("photo"              ) var photo            : Photo?            ,
  
  @JsonProperty("private_room"       ) var privateRoom      : String?           ,
  
  @Embedded
  @JsonProperty("service_area"       ) var serviceArea      : ServiceArea?      ,
  @JsonProperty("shop_detail_memo"   ) var shopDetailMemo   : String?           ,
  @JsonProperty("show"               ) var show             : String?           ,
  
  @Embedded
  @JsonProperty("small_area"         ) var smallArea        : SmallArea?        ,
  @JsonProperty("station_name"       ) var stationName      : String?           ,
  
  @Embedded
  @JsonProperty("sub_genre"          ) var subGenre         : SubGenre?         ,
  @JsonProperty("tatami"             ) var tatami           : String?           ,
  @JsonProperty("tv"                 ) var tv               : String?           ,
  
  @Embedded
  @JsonProperty("urls"               ) var urls             : Urls?             ,
  @JsonProperty("wedding"            ) var wedding          : String?           ,
  @JsonProperty("wifi"               ) var wifi             : String?           

){
   constructor():this("","","","", Budget(),
     "",0,"","","","","", CouponUrls(),
     "","","","", Genre(),"","","",0,
      LargeArea(), LargeServiceArea(),0.00,0.00,"","", MiddleArea(),"",
        "","","","","","","",0,
        "", Photo(),"", ServiceArea(),"","", SmallArea(),"",
       SubGenre(),"","",Urls(),"","")

  override fun toString(): String {
    return "Shop(access=$access, address=$address, band=$band, barrierFree=$barrierFree, budget=$budget, budgetMemo=$budgetMemo, capacity=$capacity, card=$card, catch=$catch, charter=$charter, child=$child, close=$close, couponUrls=$couponUrls, course=$course, english=$english, freeDrink=$freeDrink, freeFood=$freeFood, genre=$genre, horigotatsu=$horigotatsu, id='$id', karaoke=$karaoke, ktaiCoupon=$ktaiCoupon, largeArea=$largeArea, largeServiceArea=$largeServiceArea, lat=$lat, lng=$lng, logoImage=$logoImage, lunch=$lunch, middleArea=$middleArea, midnight=$midnight, mobileAccess=$mobileAccess, name=$name, nameKana=$nameKana, nonSmoking=$nonSmoking, open=$open, otherMemo=$otherMemo, parking=$parking, partyCapacity=$partyCapacity, pet=$pet, photo=$photo, privateRoom=$privateRoom, serviceArea=$serviceArea, shopDetailMemo=$shopDetailMemo, show=$show, smallArea=$smallArea, stationName=$stationName, subGenre=$subGenre, tatami=$tatami, tv=$tv, urls=$urls, wedding=$wedding, wifi=$wifi)"
  }


}

