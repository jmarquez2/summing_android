package com.jrms.summing.ui.spend.detail.quantity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.jrms.summing.R
import com.jrms.summing.databinding.FragmentSpendQuantityBinding
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class SpendQuantityFragment : Fragment() {

    private val viewModel : SpendDataViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentSpendQuantityBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_spend_cost_description)
        }

        binding.costEditText.doOnTextChanged { _, _, _, _ ->
            binding.costEditText.setSelection(binding.costEditText.text?.toString()?.length ?: 0)
        }

        return binding.root
    }

}