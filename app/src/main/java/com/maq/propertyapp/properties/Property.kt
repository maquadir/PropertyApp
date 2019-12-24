package com.maq.propertyapp.properties

import com.squareup.moshi.Json

//JSON to Kotlin Class

data class Property (
    @Json(name = "ad_id")
    val ad_id : Int,
    @Json(name = "data")
    var data: data
)

data class PropertyData (

    val Id: String,
    val AgencyLogoUrl: String,
    val Area: String,
    val AuctionDate: String,
    val AvailableFrom: Int?,
    val Bathrooms: Int,
    val Bedrooms: Int,
    val Carspaces: Int,
    val DateFirstListed: String,
    val DateUpdated: String,
    val Description: String,
    val DisplayPrice: String,
    val Currency: String,
    val Location: Location,
    val owner: Owner,
    val ImageUrls:Array<String>,
    val is_premium: Int,
    val isPriority: Int,
    val Latitude: Double,
    val ListingPrice: Int?,
    val ListingStatistics: Int?,
    val ListingType: String,
    val ListingTypeString: String,
    val Longitude: Double


)

data class Owner(

    val name:String,
    val lastName:String,
    val dob:String,
    var image: Image
)

data class Image(

    val big: Url,
    val small: Url,
    val medium: Url
)

data class Url(
    val url:String
)

data class Location(

    val Address:String,
    val Address2:String,
    val State:String,
    var Suburb:String
)

data class data(
    var listings: List<PropertyData>
)




