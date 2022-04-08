package com.jrms.summing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrms.summing.R
import com.jrms.summing.adapters.SpendAdapter
import org.koin.android.ext.android.inject
import com.jrms.summing.databinding.FragmentHomeBinding
import com.jrms.summing.models.Spend
import com.jrms.summing.other.ObtainableData
import com.jrms.summing.other.SPEND_LIST_EXTRA
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(){

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val bindingFragment : FragmentHomeBinding  = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.openAddSpend = this::openAddSpend
        bindingFragment.recyclerSpend.adapter = SpendAdapter(listOf())
        bindingFragment.recyclerSpend.layoutManager = LinearLayoutManager(context)
        homeViewModel.spendListLiveData.observe(viewLifecycleOwner) {
            (bindingFragment.recyclerSpend.adapter as SpendAdapter).assignList(it)

        }

        bindingFragment.viewModel = homeViewModel
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getSpendList()
    }

    private fun openAddSpend(){
        view?.findNavController()?.navigate(R.id.navigation_addSpend)
    }

}