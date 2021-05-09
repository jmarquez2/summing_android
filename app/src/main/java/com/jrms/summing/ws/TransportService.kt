package com.jrms.summing.ws

import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import com.jrms.summing.models.Transport
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransportService {
    @GET("transport")
    fun getLastTransport() : Call<List<Spend>>

    @POST("transport")
    fun saveNewTransport(@Body spend: Spend) : Call<ResponseWS>
}