package com.jrms.summing.models

import com.google.gson.annotations.SerializedName

class ResponseWS {
    @SerializedName("message")
    public var message : String? = null

    @SerializedName("description")
    public var description : String? = null
}