package com.jrms.summing.ui.addSpend

import android.app.Application
import android.location.Location
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jrms.summing.R
import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import com.jrms.summing.models.Transport
import com.jrms.summing.repositories.SpendRepository
import com.jrms.summing.repositories.WebServiceRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSpendViewModel(application: Application, private val spendRepository: SpendRepository )
    : AndroidViewModel(application) {

    lateinit var openLocation : (Boolean) -> Unit
    lateinit var returnToPrevious : () -> Unit
    lateinit var spend : Spend

    private var destinationLatitude : Double? = null
    private var destinationLongitude : Double? = null
    private var originLatitude : Double? = null
    private var originLongitude : Double? = null

    var currentLocation : Location? = null

    val addSpendObservable by lazy {
        AddSpendObservable(this@AddSpendViewModel::isTransportIndex).apply {
            setDestinationText(getApplication<com.jrms.summing.Application>().getString(R.string.setDestination))
        }
    }



    fun setOriginLocation(latitude : Double, longitude : Double){
        addSpendObservable.setOriginText(Pair(latitude, longitude))
        originLatitude = latitude
        originLongitude = longitude
    }

    fun setDestinationLocation(latitude: Double, longitude: Double){
        addSpendObservable.setDestinationText(Pair(latitude, longitude))
        destinationLatitude = latitude
        destinationLongitude = longitude
    }

    fun getOriginLocation() : Pair<Double, Double>{
        return if(originLatitude == null){
            Pair(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
        }else{
            Pair(originLatitude!!, originLongitude!!)
        }
    }

    fun getDestinationLocation() : Pair<Double, Double>{
        return if(destinationLatitude == null){
            Pair(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
        }else{
            Pair(destinationLatitude!!, destinationLongitude!!)
        }
    }

    fun saveSpend(view : View){
        val spend = Spend().apply {
            cost = addSpendObservable.getCost()!!
            description = addSpendObservable.getDescription()!!
            if(isTransportIndex(addSpendObservable.getSelectedType())){
                transport = Transport().apply {
                    originLocationLatitude = addSpendObservable.getOriginLocation().first
                    originLocationLongitude = addSpendObservable.getOriginLocation().second

                    destinationLocationLatitude = addSpendObservable.getDestinationLocation().first
                    destinationLocationLongitude = addSpendObservable.getDestinationLocation().second

                    transportReference = addSpendObservable.getReference()
                }
            }
        }
        spendRepository.saveSpend(spend).enqueue(object : Callback<ResponseWS>{
            override fun onResponse(call: Call<ResponseWS>, response: Response<ResponseWS>) {
                if(response.isSuccessful){
                    Toast.makeText(getApplication(), R.string.spentSaved, Toast.LENGTH_SHORT).show()
                    returnToPrevious()
                }else{
                    Toast.makeText(getApplication(), R.string.cannotSaveSpen, Toast.LENGTH_SHORT).show()
                    print(call)
                }
            }

            override fun onFailure(call: Call<ResponseWS>, t: Throwable) {
                Toast.makeText(getApplication(), R.string.cannotSaveSpen, Toast.LENGTH_SHORT).show()
                print(call)
            }

        })
    }

    fun getOriginLocation(view: View){
        openLocation(true)
    }

    fun getDestinationLocation(view: View){
        openLocation(false)
    }

    private fun isTransportIndex(index : Int) : Boolean{
        return index == 1
    }

}