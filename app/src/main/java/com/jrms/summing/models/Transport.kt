package com.jrms.summing.models

import com.google.gson.annotations.SerializedName

class Transport {
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("spend_id")
    var spendId : Int = 0
    @SerializedName("transport_reference")
    var transportReference : String? = null
    @SerializedName("origin_location_latitude")
    var originLocationLatitude : Double = 0.0
    @SerializedName("origin_location_longitude")
    var originLocationLongitude : Double = 0.0
    @SerializedName("destination_location_latitude")
    var destinationLocationLatitude : Double = 0.0
    @SerializedName("destination_location_longitude")
    var destinationLocationLongitude : Double = 0.0

}