package com.jrms.summing.ui.spend.detail.description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jrms.summing.R
import com.jrms.summing.databinding.FragmentSpendDescriptionBinding
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SpendDescriptionFragment : Fragment() {

    val viewModel : SpendDataViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSpendDescriptionBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.buttonDescriptionContinue.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_spend_description_detail)
        }

        return binding.root
    }


}