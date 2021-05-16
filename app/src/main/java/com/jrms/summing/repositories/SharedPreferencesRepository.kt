package com.jrms.summing.repositories

import android.content.Context

class SharedPreferencesRepository (private val context : Context){

    private val _token : String = "token"
    private val _preferenceName = "summing_preferences"


    fun getToken() : String?{
        return context.getSharedPreferences(_preferenceName, Context.MODE_PRIVATE)?.
        getString(_token, null)
    }

    fun setToken(token : String?){
        with(context.getSharedPreferences(_preferenceName, Context.MODE_PRIVATE)?.edit()){
            this?.putString(_token, token)
            this?.apply()
        }
    }
}