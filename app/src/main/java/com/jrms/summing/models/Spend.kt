package com.jrms.summing.models

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Spend {
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("cost")
    var cost: Double = 0.0
    @SerializedName("date")
    var date : Date = Date()
    @SerializedName("transport")
    var transport : Transport? = null

    fun getType() : String{
        return "T"
    }

    fun getDescription() : String?{
        return transport?.transportReference
    }

    fun getDateFormatted() : String {
        return SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(this.date)
    }

    fun getCostFormatted() : String{
        return DecimalFormat("#.00").format(cost)
    }


}