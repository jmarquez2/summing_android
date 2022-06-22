package com.jrms.summing.ui.spend.detail.location

import androidx.navigation.fragment.findNavController
import com.jrms.summing.R
import com.jrms.summing.other.SpendType
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import com.jrms.summing.ui.spend.detail.TwoOptionsQuestionFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DestinationLocationFragment : TwoOptionsQuestionFragment(
    R.string.askDestination,
    R.string.yes,
    R.string.no


)

{

    private val viewModel by sharedViewModel<SpendDataViewModel>()

    override fun optionOneClick() {
        val action = DestinationLocationFragmentDirections.
        actionNavigationDestinationLocationFragment((viewModel.currentLocation?.latitude ?:
        0.0).toString(),
            (viewModel.currentLocation?.longitude ?: 0.0).toString(), 0, false)
        findNavController().navigate(action)
    }

    override fun optionTwoClick() {
        findNavController().navigate(R.id.action_navigation_destination_cost_spend)
    }

}