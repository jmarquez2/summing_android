package com.jrms.summing.ws

import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import com.jrms.summing.models.Transport
import com.jrms.summing.other.AUTHORIZATION_HEADER
import retrofit2.Call
import retrofit2.http.*

interface SpendService {
    @GET("spend")
    fun getSpendList(@Query("limit") limit : Int,
                     @Query("offset") offset : Int,
                     @Header(AUTHORIZATION_HEADER) authentication : String) : Call<List<Spend>>

    @POST("spend")
    fun saveNewSpend(@Body spend: Spend,
                     @Header(AUTHORIZATION_HEADER) authentication : String): Call<ResponseWS>
}