package com.jrms.summing.ui.addSpend

import android.app.Application
import android.location.Location
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.jrms.summing.repositories.WebServiceRepository

class AddSpendViewModel(application: Application, val webServiceRepository: WebServiceRepository )
    : AndroidViewModel(application) {

    var location : Location? = null

    val addSpendObservable by lazy {
        AddSpendObservable()
    }

    fun saveSpend(view : View){
        println(addSpendObservable)
    }

}