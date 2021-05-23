package com.jrms.summing.repositories

import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import retrofit2.Call

class SpendRepository (private val wsRepository : WebServiceRepository,
                       private val sharedPreferencesRepository: SharedPreferencesRepository) {

    fun getSpendList(limit : Int = 20, offset : Int = 0) : Call<List<Spend>>{
        return wsRepository.serviceSpend.getSpendList(limit, offset,
            sharedPreferencesRepository.getRequestAuthHeader())
    }

    fun saveSpend(spend :Spend) : Call<ResponseWS>{
        return wsRepository.serviceSpend.saveNewSpend(spend,
            sharedPreferencesRepository.getRequestAuthHeader())
    }


}