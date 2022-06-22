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
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jrms.summing.R
import com.jrms.summing.other.*
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LocationFragment : Fragment() {

    private var marker : Marker? = null

    private val args : LocationFragmentArgs by navArgs()
    private val viewModel: SpendDataViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.location_fragment, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val map = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.mapLayout, map).commit()
        map.getMapAsync { m->

            val latitudeLongitude = LatLng(args.latitude.toDouble(), args.longitude.toDouble())
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

                        if(args.type == 1){
                            viewModel.setOriginLocation(latLan.latitude, latLan.longitude)
                            if(args.fromDetails){
                                findNavController().navigateUp()
                            }else{
                                findNavController().navigate(R.id.navigation_destination_fragment)
                            }

                        }else{
                            viewModel.setDestinationLocation(latLan.latitude, latLan.longitude)
                            if(args.fromDetails){
                                findNavController().navigateUp()
                            }else{
                                findNavController().navigate(R.id.navigation_cost_spend_fragment)
                            }

                        }


                    }
                    .setNegativeButton(R.string.no, null)
                    .show()

            }
        }
    }

}