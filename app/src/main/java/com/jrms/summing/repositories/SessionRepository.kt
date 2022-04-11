package com.jrms.summing.repositories

import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Token
import com.jrms.summing.models.User
import retrofit2.Call

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class SessionRepository(private val webServiceRepository: WebServiceRepository,
                        private val sharedPreferencesRepository: SharedPreferencesRepository) {

    fun logout() {
        sharedPreferencesRepository.setToken(null)
    }

    fun login(username: String, password: String) : Call<Token>{
        return webServiceRepository.serviceAuthentication.login(User(username, password))
    }

    fun setToken(token : String){
        sharedPreferencesRepository.setToken(token)
    }

}