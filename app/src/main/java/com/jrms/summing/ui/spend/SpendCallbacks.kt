package com.jrms.summing.ui.spend

import com.jrms.summing.models.Spend

interface SpendCallbacks {
    fun openAddSpend ()
    fun addToList(list : List<Spend>)
    fun clearData()
}