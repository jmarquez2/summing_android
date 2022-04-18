package com.jrms.summing.other

import android.os.Parcel
import com.jrms.summing.models.Spend

class SpendView() : Spend(){

    private lateinit var isSelectedFunc : () -> Boolean

    constructor(spend: Spend, selected : () -> Boolean) : this() {
        this.cost = spend.cost
        this.date = spend.date
        this.description = spend.description
        this.transport = spend.transport
        this.isSelectedFunc = selected
    }

    val selected : Boolean
    get() {
        return isSelectedFunc()
    }

}