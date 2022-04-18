package com.jrms.summing.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jrms.summing.R
import com.jrms.summing.adapters.SpendAdapter
import com.jrms.summing.databinding.FragmentSpendBinding
import org.koin.android.viewmodel.ext.android.viewModel


class SpendFragment : Fragment(), ActionMode.Callback{

    private val spendViewModel: SpendViewModel by viewModel()
    private var actionMode: ActionMode? = null
    private var spendAdapter : SpendAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val bindingFragment : FragmentSpendBinding = FragmentSpendBinding.inflate(inflater,
            container, false)
        spendViewModel.openAddSpend = this::openAddSpend
        spendAdapter = SpendAdapter(listOf(), this::actionContext,
            this::isActionModeActive, this::cancelAction)
        bindingFragment.recyclerSpend.adapter = spendAdapter
        bindingFragment.recyclerSpend.layoutManager = LinearLayoutManager(context)
        spendViewModel.spendListLiveData.observe(viewLifecycleOwner) {
            (bindingFragment.recyclerSpend.adapter as SpendAdapter).assignList(it)

        }

        bindingFragment.viewModel = spendViewModel
        return bindingFragment.root
    }

    private fun actionContext(){
        actionMode = activity?.startActionMode(this)
    }

    private fun isActionModeActive() : Boolean{
        return actionMode != null
    }

    private fun cancelAction(){
        actionMode?.finish()
        actionMode = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spendViewModel.getSpendList()
    }

    private fun openAddSpend(){
        view?.findNavController()?.navigate(R.id.navigation_addSpend)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.action_menu_options, menu)
        mode?.setTitle(R.string.spendSelection)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
        when(p1?.itemId){
            R.string.deleteSpend -> {
                Log.w("delete spend", "option to do")
            }
        }
        actionMode?.finish()
        actionMode = null
        return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        actionMode = null
        spendAdapter?.removeSelections()
    }

}