package com.jrms.summing.other

import com.jrms.summing.models.Spend

interface SpendItemsOption {
    fun addSpend(position : Int)
    fun removeSpend(position : Int)
    fun isPresent(position : Int) : Boolean
}