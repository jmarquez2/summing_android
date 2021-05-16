package com.jrms.summing.ui.addSpend

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.jrms.summing.R
import com.jrms.summing.databinding.AddSpendFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel


class AddSpendFragment : Fragment(), LocationListener{

    private val viewModel: AddSpendViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddSpendFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.costValue.doOnTextChanged { text, start, before, count ->
            binding.costValue.setSelection(binding.costValue.text?.toString()?.length ?: 0)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when (PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                getLocation(true)

            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 22
                )
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {

            22 -> {
                getLocation(grantResults.isNotEmpty() &&
                        grantResults[0] == PERMISSION_GRANTED)
            }
        }

    }

    private fun getLocation(canGet: Boolean){
        if(canGet){
            viewModel.addSpendObservable.setCanUseLocation(true)
            val locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(GPS_PROVIDER, 0, 0f, this)
            }

        }
    }

    override fun onLocationChanged(location: Location) {
        viewModel.location = location
    }


}