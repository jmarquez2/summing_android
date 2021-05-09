package com.jrms.summing.models

import com.google.gson.annotations.SerializedName

class Transport {
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("spend_id")
    var spendId : Int = 0
    @SerializedName("transport_reference")
    var transportReference : String? = null
    @SerializedName("origin_location")
    var originLocation : Double = 0.0
    @SerializedName("destination_location")
    var destinationLocation : Double = 0.0

}