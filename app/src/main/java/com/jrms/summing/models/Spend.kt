package com.jrms.summing.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Spend() : Parcelable{
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("cost")
    var cost: Double = 0.0
    @SerializedName("date")
    var date : Date = Date()
    @SerializedName("transport")
    var transport : Transport? = null
    @SerializedName("description")
    var description : String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        cost = parcel.readDouble()
        val longDate = parcel.readLong()
        date = if (longDate == -1L) Date() else Date(longDate)
        transport = parcel.readParcelable(Transport::class.java.classLoader)
        description = parcel.readString()
    }


    fun getType() : String{
        return if (description == null){
            "G"
        }else{
            "T"
        }
    }


    fun getDateFormatted() : String {
        return SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(this.date)
    }

    fun getCostFormatted() : String{
        return DecimalFormat("#.00").format(cost)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(cost)
        parcel.writeLong(date.time)
        parcel.writeParcelable(transport, flags)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Spend> {
        override fun createFromParcel(parcel: Parcel): Spend {
            return Spend(parcel)
        }

        override fun newArray(size: Int): Array<Spend?> {
            return arrayOfNulls(size)
        }
    }


}