package com.jrms.summing.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrms.summing.R
import com.jrms.summing.adapters.SpendAdapter
import com.jrms.summing.databinding.FragmentSpendBinding
import org.koin.android.viewmodel.ext.android.viewModel


class SpendFragment : Fragment(), ActionMode.Callback {

    private val spendViewModel: SpendViewModel by viewModel()
    private var actionMode: ActionMode? = null
    private var spendAdapter: SpendAdapter? = null
    private var bindingFragment : FragmentSpendBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bindingFragment = FragmentSpendBinding.inflate(inflater,
            container, false)
        spendViewModel.openAddSpend = this::openAddSpend
        spendAdapter = SpendAdapter(this::actionContext,
            this::isActionModeActive, this::cancelAction)
        bindingFragment?.recyclerSpend?.adapter = spendAdapter
        bindingFragment?.recyclerSpend?.layoutManager = LinearLayoutManager(context)
        spendViewModel.spendListLiveData.observe(viewLifecycleOwner) {
            (bindingFragment?.recyclerSpend?.adapter as SpendAdapter?)?.assignList(it)

        }

        bindingFragment?.refreshSpend?.setOnRefreshListener {
            reloadSpendList()
        }

        bindingFragment?.recyclerSpend?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if ((bindingFragment?.recyclerSpend?.layoutManager as LinearLayoutManager?)?.
                    findLastCompletelyVisibleItemPosition() == (spendAdapter?.itemCount ?: 1) - 1
                ){
                    bindingFragment?.refreshSpend?.isRefreshing = true
                    spendViewModel.getSpendList {
                        bindingFragment?.refreshSpend?.isRefreshing = false
                    }
                }
            }
        })

        bindingFragment?.viewModel = spendViewModel
        return bindingFragment?.root!!
    }

    private fun reloadSpendList() {
        cancelAction()
        spendAdapter?.clearData()
        spendViewModel.getSpendList(true) {
            bindingFragment?.refreshSpend?.isRefreshing = false
        }
    }

    private fun actionContext() {
        actionMode = activity?.startActionMode(this)
    }

    private fun isActionModeActive(): Boolean {
        return actionMode != null
    }

    private fun cancelAction() {
        actionMode?.finish()
        actionMode = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spendViewModel.getSpendList{
            bindingFragment?.refreshSpend?.isRefreshing = false
        }
    }

    private fun openAddSpend() {
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
        when (p1?.itemId) {
            R.id.delete_spend_option -> {
                val selections = spendAdapter?.getSelections()
                if(selections != null){
                    spendViewModel.deleteSelection(selections,{
                        Toast.makeText(context, R.string.spendsDeleted, Toast.LENGTH_SHORT).show()
                        actionMode?.finish()
                        actionMode = null
                        reloadSpendList()


                    }, {
                        Toast.makeText(context, R.string.errorDeletingSpends,
                            Toast.LENGTH_SHORT).show()

                    })
                }

            }else -> {
                actionMode?.finish()
                actionMode = null
            }
        }

        return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        actionMode = null
        spendAdapter?.removeSelections()
    }

    override fun onDestroyView() {
        bindingFragment = null
        super.onDestroyView()
    }

}