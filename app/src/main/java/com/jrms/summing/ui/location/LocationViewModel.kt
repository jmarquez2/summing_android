package com.jrms.summing.ui.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    var origin = true
    var latitude : Double = 0.0
    var longitude : Double = 0.0
}