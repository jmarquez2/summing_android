package com.jrms.summing.ui.spend.detail.type

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.jrms.summing.R
import com.jrms.summing.databinding.FragmentSpendTypeBinding
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class SpendTypeFragment : Fragment() {

     private val spendDataViewModel : SpendDataViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // Inflate the layout for this fragment
        val binding = FragmentSpendTypeBinding.inflate(inflater, container, false)
        binding.genericSpendButton.setOnClickListener(this::clickType)
        binding.transportSpendButton.setOnClickListener(this::clickType)
        return binding.root

    }



    private fun clickType(view : View){
        when(view.id){
            R.id.genericSpendButton -> {
                spendDataViewModel.addSpendObservable.setSelectedType(0)
                findNavController().navigate(R.id.action_navigation_cost_spend_fragment)
            }
            R.id.transportSpendButton ->{
                spendDataViewModel.addSpendObservable.setSelectedType(1)
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) -> {
                        findNavController().navigate(R.id.navigation_origin_fragment)

                    }
                    else -> {
                        findNavController().navigate(R.id.action_navigation_ask_user_location_fragment)
                        /*registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ granted ->
                            getLocation(granted.values.all {
                                it == true
                            })
                        }.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION))*/

                    }
                }

            }
        }

    }


}