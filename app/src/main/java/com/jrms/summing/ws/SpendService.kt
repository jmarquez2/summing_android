package com.jrms.summing.ws

import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import com.jrms.summing.other.AUTHORIZATION_HEADER
import retrofit2.http.*

interface SpendService {
    @GET("spend")
    suspend fun getSpendList(@Query("limit") limit : Int,
                     @Query("offset") offset : Int,
                     @Header(AUTHORIZATION_HEADER) authentication : String) : List<Spend>

    @POST("spend")
    suspend fun saveNewSpend(@Body spend: Spend,
                     @Header(AUTHORIZATION_HEADER) authentication : String): ResponseWS

    @DELETE("spend")
    suspend fun deleteSpends(@Query("ids") ids : String,
                     @Header(AUTHORIZATION_HEADER) authentication: String) : ResponseWS
}