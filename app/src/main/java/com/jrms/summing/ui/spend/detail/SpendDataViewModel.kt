package com.jrms.summing.ui.spend.detail

import android.app.Application
import android.location.Location
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jrms.summing.R
import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import com.jrms.summing.models.Transport
import com.jrms.summing.other.SpendType
import com.jrms.summing.repositories.SpendRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class SpendDataViewModel(application: Application, private val spendRepository: SpendRepository) :
    AndroidViewModel(application) {

    lateinit var spend: Spend

    private var destinationLatitude: Double? = null
    private var destinationLongitude: Double? = null
    private var originLatitude: Double? = null
    private var originLongitude: Double? = null

    var currentLocation: Location? = null


    var addSpendObservable = SpendDataObservable().apply {
        setDestinationText(getApplication<com.jrms.summing.Application>().getString(R.string.setDestination))
    }


    fun getType() : String{
        return when(addSpendObservable.getSelectedType()){
            SpendType.GENERAL.ordinal -> {
                getApplication<Application>().getString(R.string.generic)
            }
            else -> {
                getApplication<Application>().getString(R.string.transport)
            }
        }
    }

    fun setOriginLocation(latitude: Double, longitude: Double) {
        addSpendObservable.setOriginText(Pair(latitude, longitude))
        originLatitude = latitude
        originLongitude = longitude
    }

    fun setDestinationLocation(latitude: Double, longitude: Double) {
        addSpendObservable.setDestinationText(Pair(latitude, longitude))
        destinationLatitude = latitude
        destinationLongitude = longitude
    }

    fun getOriginLocation(): Pair<Double, Double> {
        return if (originLatitude == null) {
            Pair(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
        } else {
            Pair(originLatitude!!, originLongitude!!)
        }
    }

    fun getDestinationLocation(): Pair<Double, Double> {
        return if (destinationLatitude == null) {
            Pair(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
        } else {
            Pair(destinationLatitude!!, destinationLongitude!!)
        }
    }

    suspend fun saveSpend(): ResponseWS {
        val spend = Spend().apply {
            cost = addSpendObservable.getCost()!!
            description = addSpendObservable.getDescription()!!
            if (addSpendObservable.getIsTransport()) {
                transport = Transport().apply {
                    originLocationLatitude = addSpendObservable.getOriginLocation().first
                    originLocationLongitude = addSpendObservable.getOriginLocation().second

                    destinationLocationLatitude = addSpendObservable.getDestinationLocation().first
                    destinationLocationLongitude =
                        addSpendObservable.getDestinationLocation().second

                    transportReference = addSpendObservable.getReference()
                }
            }
        }
        val result = spendRepository.saveSpend(spend)
        addSpendObservable = SpendDataObservable().apply {
            setDestinationText(getApplication<com.jrms.summing.Application>().
            getString(R.string.setDestination))
        }
        return result
    }






}