package com.jrms.summing.ui.addSpend

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR


class AddSpendObservable : BaseObservable() {

    private var _canUseLocation : Boolean = false
    private var _cost : Double? = null
    private var _description : String? = null
    private var _originText : String?= null
    private var _destinationText : String? = null
    private var _reference  : String? = null
    private var _originLocation : Double = 0.0
    private var _destinationLocation : Double = 0.0

    @Bindable
    fun getCost(): Double?{
        return _cost
    }

    fun setCost(cost : Double?){
        if(this._cost != cost){
            this._cost = cost
            notifyPropertyChanged(BR.cost)
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

    fun setOriginText(origin : String?){
        if(origin?.equals(_originText) == false){
            _originText = origin
           notifyPropertyChanged(BR.originText)
        }
    }

    @Bindable
    fun getDestinationText() : String?{
        return _destinationText
    }

    fun setDestinationText(destinationText : String?){
        if(destinationText?.equals(_destinationText) == false){
            _destinationText = destinationText
            notifyPropertyChanged(BR.destinationText)
        }
    }

    @Bindable
    fun getReference() : String?{
        return _reference
    }

    fun setReference(reference : String?){
        if(reference?.equals(_reference) == false){
            _reference = reference
            notifyPropertyChanged(BR.reference)
        }
    }
}