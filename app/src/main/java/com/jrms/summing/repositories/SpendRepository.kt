package com.jrms.summing.repositories

import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import retrofit2.Call

class SpendRepository (private val wsRepository : WebServiceRepository,
                       private val sharedPreferencesRepository: SharedPreferencesRepository) {

    suspend fun getSpendList(limit : Int = 20, offset : Int = 0) : List<Spend>{
        return wsRepository.serviceSpend.getSpendList(limit, offset,
            sharedPreferencesRepository.getRequestAuthHeader())
    }

    suspend fun saveSpend(spend :Spend) : ResponseWS{
        return wsRepository.serviceSpend.saveNewSpend(spend,
            sharedPreferencesRepository.getRequestAuthHeader())
    }

    suspend fun deleteSpends(spends : String) : ResponseWS{
        return wsRepository.serviceSpend.deleteSpends(spends,
        sharedPreferencesRepository.getRequestAuthHeader())
    }


}