package com.jrms.summing.repositories

import android.content.Context
import com.google.gson.GsonBuilder
import com.jrms.summing.R
import com.jrms.summing.ws.TransportService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebServiceRepository (context : Context){

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    private var retrofit = Retrofit.Builder()
        .baseUrl(context.getString(R.string.ws_url))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    val serviceTransport = retrofit.create(TransportService::class.java)


}