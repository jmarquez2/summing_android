package com.jrms.summing.ui.location

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jrms.summing.R
import com.jrms.summing.other.LATITUDE_RESULT
import com.jrms.summing.other.LOCATION_RESULT_DESTINATION
import com.jrms.summing.other.LOCATION_RESULT_ORIGIN
import com.jrms.summing.other.LONGITUDE_RESULT
import org.koin.android.viewmodel.ext.android.viewModel

class LocationFragment : Fragment() {

    private var marker : Marker? = null

    private val viewModel: LocationViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.latitude = (arguments?.
        getString("latitude"))?.toDouble() ?: 0.0
        viewModel.longitude = (arguments?.getString("longitude"))?.toDouble() ?: 0.0
        viewModel.origin = (arguments?.getBoolean("type") ?: true)
        return inflater.inflate(R.layout.location_fragment, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val map = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.mapLayout, map).commit()
        map.getMapAsync { m->

            val latitudeLongitude = LatLng(viewModel.latitude, viewModel.longitude)
            marker = m.addMarker(MarkerOptions().position(latitudeLongitude).
            title(requireContext().getString(R.string.selectLocation)))

            m.moveCamera(CameraUpdateFactory.newLatLngZoom(latitudeLongitude, 12.6f))

            m.setOnMapClickListener{latLan ->
                marker?.remove()
                marker = m.addMarker(MarkerOptions().position(latLan).
                title(requireContext().getString(R.string.selectLocation)))
                AlertDialog.Builder(requireContext()).setTitle(R.string.confirm)
                    .setMessage(R.string.askUseLocation)
                    .setPositiveButton(R.string.yes) { _, _ ->

                        setFragmentResult(
                            if(viewModel.origin) LOCATION_RESULT_ORIGIN else
                                LOCATION_RESULT_DESTINATION,
                            bundleOf(
                                LATITUDE_RESULT to latLan.latitude,
                                LONGITUDE_RESULT to latLan.longitude
                            )
                        )
                        findNavController().navigateUp()

                    }
                    .setNegativeButton(R.string.no, null)
                    .show()

            }
        }
    }

}