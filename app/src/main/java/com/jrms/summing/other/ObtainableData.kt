package com.jrms.summing.other

interface ObtainableData {
    fun <T> getData(key : Any? = null) : T?
}