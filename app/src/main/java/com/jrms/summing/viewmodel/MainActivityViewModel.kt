package com.jrms.summing.viewmodel

import androidx.lifecycle.ViewModel
import com.jrms.summing.repositories.SessionRepository

class MainActivityViewModel(private val sessionRepository: SessionRepository) : ViewModel(){

    fun logout(){
        sessionRepository.logout()
    }
}