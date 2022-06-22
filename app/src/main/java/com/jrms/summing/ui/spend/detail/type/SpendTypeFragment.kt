package com.jrms.summing.ui.spend.detail.type

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.jrms.summing.R
import com.jrms.summing.other.SpendType
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import com.jrms.summing.ui.spend.detail.TwoOptionsQuestionFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel


class SpendTypeFragment : TwoOptionsQuestionFragment(
    R.string.askSpendType,
    R.string.generic,
    R.string.transport
) {

     private val spendDataViewModel : SpendDataViewModel by sharedViewModel()


    override fun optionOneClick() {
        spendDataViewModel.addSpendObservable.setSelectedType(SpendType.GENERAL.ordinal)
        findNavController().navigate(R.id.action_navigation_cost_spend_fragment)
    }

    override fun optionTwoClick() {
        spendDataViewModel.addSpendObservable.setSelectedType(SpendType.TRANSPORT.ordinal)
        if(ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){
            findNavController().navigate(R.id.action_navigation_origin_fragment)
        }else{
            findNavController().navigate(R.id.action_navigation_cost_spend_fragment)
        }

    }




}