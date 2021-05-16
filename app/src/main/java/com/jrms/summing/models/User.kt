package com.jrms.summing.models

import com.google.gson.annotations.SerializedName

class User (){

    constructor(username : String, password: String) : this(){
        this.password = password
        this.username = username
    }

    @SerializedName("username")
    lateinit var username : String
    @SerializedName("password")
    lateinit var password : String
}