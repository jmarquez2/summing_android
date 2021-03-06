package com.jrms.summing.ui.spend.detail

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jrms.summing.R
import com.jrms.summing.databinding.SpendDetailFragmentBinding
import com.jrms.summing.models.Spend
import com.jrms.summing.other.LATITUDE_RESULT
import com.jrms.summing.other.LOCATION_RESULT_DESTINATION
import com.jrms.summing.other.LOCATION_RESULT_ORIGIN
import com.jrms.summing.other.LONGITUDE_RESULT
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.lang.Exception


class SpendDetailFragment : Fragment(), LocationListener, View.OnClickListener {

    private val dataViewModel: SpendDataViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SpendDetailFragmentBinding.inflate(inflater, container, false)

        binding.buttonSaveSpend.setOnClickListener(this)



        binding.viewModel = dataViewModel

        setFragmentResultListener(LOCATION_RESULT_ORIGIN) { _, bundle ->
            dataViewModel.setOriginLocation(
                bundle.getDouble(LATITUDE_RESULT), bundle.getDouble(
                    LONGITUDE_RESULT
                )
            )
        }
        setFragmentResultListener(LOCATION_RESULT_DESTINATION) { _, bundle ->
            dataViewModel.setDestinationLocation(
                bundle.getDouble(LATITUDE_RESULT), bundle.getDouble(
                    LONGITUDE_RESULT
                )
            )
        }

        return binding.root
    }

    private fun getLocation(canGet: Boolean) {
        if (canGet) {
            dataViewModel.addSpendObservable.setCanUseLocation(true)
            val locationManager =
                requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
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
                locationManager.requestLocationUpdates(GPS_PROVIDER, 0, 0f, this)

            }

        }
    }

    override fun onLocationChanged(location: Location) {
        dataViewModel.currentLocation = location
    }

    override fun onProviderDisabled(provider: String) {

    }

    override fun onProviderEnabled(provider: String) {

    }



    private fun goToLocation(isOrigin: Boolean) {
        val location = if (isOrigin) dataViewModel.getOriginLocation() else
            dataViewModel.getDestinationLocation()
        val locationArgs = bundleOf(
            "latitude" to location.first.toString(),
            "longitude" to location.second.toString(), "type" to isOrigin
        )
        //view?.findNavController()?.navigate(R.id.navigation_location, locationArgs)

    }

    private fun setLocation(location: Location?) {
        dataViewModel.currentLocation = location
        dataViewModel.setOriginLocation(
            dataViewModel.currentLocation?.latitude ?: 0.0,
            dataViewModel.currentLocation?.longitude ?: 0.0
        )

    }

    override fun onClick(p0: View?) {
        lifecycleScope.launch {
            try {
                val result = dataViewModel.saveSpend()
                Log.d("Save spend", result.message ?: "Empty message")
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_navigation_spend_details_home)
            } catch (e: Exception) {
                Log.e("Save spend", "Error", e)
                Toast.makeText(context, R.string.cannotSaveSpen, Toast.LENGTH_SHORT).show()
            }
        }

    }


}


