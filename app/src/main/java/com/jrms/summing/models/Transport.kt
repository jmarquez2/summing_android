package com.jrms.summing.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Transport() : Parcelable{
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

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        spendId = parcel.readInt()
        transportReference = parcel.readString()
        originLocationLatitude = parcel.readDouble()
        originLocationLongitude = parcel.readDouble()
        destinationLocationLatitude = parcel.readDouble()
        destinationLocationLongitude = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(spendId)
        parcel.writeString(transportReference)
        parcel.writeDouble(originLocationLatitude)
        parcel.writeDouble(originLocationLongitude)
        parcel.writeDouble(destinationLocationLatitude)
        parcel.writeDouble(destinationLocationLongitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transport> {
        override fun createFromParcel(parcel: Parcel): Transport {
            return Transport(parcel)
        }

        override fun newArray(size: Int): Array<Transport?> {
            return arrayOfNulls(size)
        }
    }

}