package com.jrms.summing.ws

import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Token
import com.jrms.summing.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("login")
    fun login(@Body user : User) : Call<Token>

    @POST("register")
    fun register(@Body user : User) : Call<ResponseWS>
}