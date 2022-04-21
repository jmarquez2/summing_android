package com.jrms.summing.ws

import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Token
import com.jrms.summing.models.User
import com.jrms.summing.other.AUTHORIZATION_HEADER
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationService {

    @POST("login")
    suspend fun login(@Body user : User) : Token

    @GET("login")
    suspend fun login(@Header(AUTHORIZATION_HEADER) token : String?) : ResponseWS

    @POST("register")
    suspend fun register(@Body user : User) : ResponseWS
}