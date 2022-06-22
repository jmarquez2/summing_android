package com.jrms.summing.ui.spend.detail.location


import androidx.navigation.fragment.findNavController
import com.jrms.summing.R
import com.jrms.summing.other.SpendType
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import com.jrms.summing.ui.spend.detail.TwoOptionsQuestionFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel


class OriginLocationFragment : TwoOptionsQuestionFragment(
    R.string.askOrigin,
    R.string.currentLocation,
    R.string.fromMap
) {

    private val viewModel by sharedViewModel<SpendDataViewModel>()

    override fun optionOneClick() {
        findNavController().navigate(R.id.navigation_destination_fragment)
    }

    override fun optionTwoClick() {
        val action = OriginLocationFragmentDirections.
        actionNavigationOriginMapLocation((viewModel.currentLocation?.latitude ?: 0.0).toString(),
            (viewModel.currentLocation?.longitude ?: 0.0).toString(), 1, false)
        findNavController().navigate(action)
    }




}