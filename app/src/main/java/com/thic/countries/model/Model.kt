package com.thic.countries.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Model(
    @SerializedName("name")
    var countryName:String?,
    @SerializedName("region")
    var countryRegion:String?,
    @SerializedName("capital")
    var countryCapital:String?,
    @SerializedName("currency")
    var countryCurrency:String?,
    @SerializedName("flag")
    var countryFlag:String?,
    @SerializedName("language")
    var countryLanguage:String?
    ){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
