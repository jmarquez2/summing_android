package com.jrms.summing.other

import android.widget.EditText
import androidx.databinding.InverseMethod
import java.lang.Exception
import java.text.DecimalFormat

object Converter {
    @JvmStatic
    @InverseMethod("stringToDouble")
    fun doubleToString(value : Double?) : String?{
        return try{
            DecimalFormat("#.##").format(value)
        }catch (e : Exception){
            null
        }

    }

    @JvmStatic
    fun stringToDouble(value: String?): Double?{
        return try {
            value?.toDouble()
        }catch (ex : Exception){
            return null
        }
    }

}