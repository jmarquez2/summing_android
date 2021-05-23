package com.jrms.summing.ui.addSpend

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jrms.summing.R
import com.jrms.summing.databinding.AddSpendFragmentBinding
import com.jrms.summing.models.Spend
import com.jrms.summing.models.Transport
import com.jrms.summing.other.LATITUDE_RESULT
import com.jrms.summing.other.LOCATION_RESULT_DESTINATION
import com.jrms.summing.other.LOCATION_RESULT_ORIGIN
import com.jrms.summing.other.LONGITUDE_RESULT
import org.koin.android.viewmodel.ext.android.viewModel


class AddSpendFragment : Fragment(), LocationListener{

    private val viewModel: AddSpendViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddSpendFragmentBinding.inflate(inflater, container, false)

        viewModel.spend = Spend()
        viewModel.openLocation = this::goToLocation

        viewModel.returnToPrevious =  {
            findNavController().navigateUp()
        }

        binding.viewModel = viewModel
        binding.costValue.doOnTextChanged { _, _, _, _ ->
            binding.costValue.setSelection(binding.costValue.text?.toString()?.length ?: 0)
        }
        setFragmentResultListener(LOCATION_RESULT_ORIGIN){ _, bundle ->
            viewModel.setOriginLocation(bundle.getDouble(LATITUDE_RESULT), bundle.getDouble(
                LONGITUDE_RESULT))
        }
        setFragmentResultListener(LOCATION_RESULT_DESTINATION){ _, bundle ->
            viewModel.setDestinationLocation(bundle.getDouble(LATITUDE_RESULT), bundle.getDouble(
                LONGITUDE_RESULT))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                setLocation(locationManager.getLastKnownLocation(NETWORK_PROVIDER))
                locationManager.requestLocationUpdates(GPS_PROVIDER, 0,0f, this)

            }

        }
    }

    override fun onLocationChanged(location: Location) {
        viewModel.currentLocation = location
    }

    private fun goToLocation(isOrigin : Boolean) {
        val location = if(isOrigin) viewModel.getOriginLocation() else
            viewModel.getDestinationLocation()
        val locationArgs = bundleOf("latitude" to location.first.toString(),
            "longitude" to location.second.toString(), "type" to isOrigin)
        view?.findNavController()?.navigate(R.id.navigation_location, locationArgs)

    }

    private fun setLocation(location : Location?){
        viewModel.currentLocation = location
        viewModel.setOriginLocation(viewModel.currentLocation?.latitude ?: 0.0,
            viewModel.currentLocation?.longitude ?: 0.0)

    }


}


