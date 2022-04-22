package com.jrms.summing.ui.spend.detail.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jrms.summing.R
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class OriginLocationFragment : Fragment() {

    private val viewModel by sharedViewModel<SpendDataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_origin_location, container, false)
    }


}