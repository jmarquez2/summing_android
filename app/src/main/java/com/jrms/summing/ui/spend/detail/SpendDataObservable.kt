package com.jrms.summing.ui.spend.detail

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR


class SpendDataObservable() : BaseObservable() {

    private var _canUseLocation : Boolean = false
    private var _cost : Double? = null
    private var _description : String? = null
    private var _originText : String?= null
    private var _destinationText : String? = null
    private var _origin : Pair<Double, Double>? = null
    private var _destination : Pair<Double, Double>? = null
    private var _reference  : String? = null
    private var _selectedType : Int = 0
    private var _isValid : Boolean = false

    @Bindable
    fun getCost(): Double?{
        return _cost
    }

    fun setCost(cost : Double?){
        if(this._cost != cost){
            this._cost = cost
            notifyPropertyChanged(BR.cost)
            validate()
        }
    }

    @Bindable
    fun getDescription() : String?{
        return _description
    }

    fun setDescription(description : String?){
        if(description?.equals(_description) == false){
            this._description = description
            notifyPropertyChanged(BR.description)
            validate()
        }
    }

    @Bindable
    fun getCanUseLocation() : Boolean{
        return _canUseLocation
    }

    fun setCanUseLocation(canUseLocation : Boolean){
        this._canUseLocation = canUseLocation
    }

    @Bindable
    fun getOriginText() : String?{
        return _originText
    }

    fun getOriginLocation() : Pair<Double, Double>{
        return _origin!!
    }

    fun getDestinationLocation() : Pair<Double, Double>{
        return _destination!!
    }

    fun setOriginText(location : Pair<Double, Double>?){
        _origin = location
        val origin = getLocationString(location)
        if(origin != _originText){
            _originText = origin
           notifyPropertyChanged(BR.originText)
            validate()
        }
    }



    @Bindable
    fun getDestinationText() : String?{
        return _destinationText
    }

    fun setDestinationText(location : Pair<Double, Double>?){
        _destination = location
        val destinationText = getLocationString(location)
        if(destinationText != _destinationText){
            _destinationText = destinationText
            notifyPropertyChanged(BR.destinationText)
            validate()
        }
    }

    fun setDestinationText(text : String){
        if(text != _destinationText){
            _destinationText = text
            notifyPropertyChanged(BR.destinationText)
            validate()
        }
    }



    private fun getLocationString(location : Pair<Double, Double>?) : String{
        val latitude = location?.first ?: 0.0
        val longitude = location?.second ?: 0.0

        return "$latitude, $longitude"
    }


    @Bindable
    fun getReference() : String?{
        return _reference
    }

    fun setReference(reference : String?){
        if(reference?.equals(_reference) == false){
            _reference = reference
            notifyPropertyChanged(BR.reference)
            validate()
        }
    }

    private fun isTransportCall(type : Int) : Boolean{
        return type == 1
    }

    @Bindable
    fun getIsTransport() : Boolean{
        return isTransportCall(_selectedType)
    }


    @Bindable
    fun getSelectedType() : Int{
        return _selectedType
    }

    fun setSelectedType(item : Int){
        if(_selectedType != item) {
            _selectedType = item
            notifyPropertyChanged(BR.selectedType)
            notifyPropertyChanged(BR.transportVisibility)
            validate()
        }
    }

    @Bindable
    fun getTransportVisibility() : Int{
        return if(isTransportCall(getSelectedType())) View.VISIBLE else View.GONE
    }

    @Bindable
    fun getIsValid() : Boolean{
        return _isValid
    }

    private fun validate(){
        val isTransport = isTransportCall(getSelectedType())
        val valid = _cost ?: 0.0 > 0.0 && (if(isTransport) true else _description?.length ?: 0 > 0) && (
                    if (isTransport)
                    _origin != null && _destination != null else true
                )
        if(_isValid != valid) {
            _isValid = valid
            notifyPropertyChanged(com.jrms.summing.BR.isValid)
        }


    }


}