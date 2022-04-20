package com.jrms.summing.adapters

import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jrms.summing.R
import com.jrms.summing.databinding.SpendItemBinding
import com.jrms.summing.models.Spend
import com.jrms.summing.other.SpendItemsOption
import com.jrms.summing.other.SpendView
import com.jrms.summing.other.ViewCallback

class SpendAdapter(
    private val actionClick: () -> Unit,
    private val isActionMenuActive: () -> Boolean,
    private val cancelAction: () -> Unit
) :
    RecyclerView.Adapter<SpendAdapter.SpendViewHolder>(), SpendItemsOption {

    private var list: ArrayList<Spend> = ArrayList()
    private val sparseArray: SparseArray<Boolean> = SparseArray()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendViewHolder {
        val binding = DataBindingUtil.inflate<SpendItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.spend_item, parent, false
        )
        return SpendViewHolder(binding.root, binding, this, isActionMenuActive)
    }

    override fun onBindViewHolder(holder: SpendViewHolder, position: Int) {
        holder.bind(position, list[position])
    }

    fun assignList(list : ArrayList<Spend>) {
        this.list = list
        notifyItemRangeChanged(0, this.list.size)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun addSpend(position: Int) {
        if(!isActionMenuActive()){
            actionClick()
        }
        sparseArray.put(position, true)
        notifyItemChanged(position)
    }

    override fun removeSpend(position: Int) {
        sparseArray.remove(position)
        notifyItemChanged(position)
        if (sparseArray.size() == 0) {
            cancelAction()
        }
    }

    override fun isPresent(position: Int): Boolean {
        return sparseArray.get(position, false)
    }

    fun removeSelections(){
        sparseArray.clear()
        notifyItemRangeChanged(0, list.size)

    }

    fun getSelections() : String{
        val stringBuilder = StringBuilder()
        sparseArray.forEach { key, value ->
            if(value){
                stringBuilder.append(",").append(list[key].id)
            }
        }
        return stringBuilder.toString().trim(',')
    }

    fun clearData(){
        val count = list.size
        list.clear()
        sparseArray.clear()
        notifyItemRangeRemoved(0, count)
    }


    class SpendViewHolder(
        itemView: View, private val binding: SpendItemBinding,
        private val spendOptions: SpendItemsOption,
        private val isActionMenuActive: () -> Boolean
    ) :
        RecyclerView.ViewHolder(itemView) {

        lateinit var spend: Spend

        fun bind(position: Int, spend: Spend) {
            this.spend = spend
            binding.spend = SpendView(spend) {
                this.spendOptions.isPresent(position)
            }
            binding.clickListener = object : ViewCallback {
                override fun click(view: View?) {
                    if (isActionMenuActive()) {
                        selectOrRemove(position)
                    } else {
                        Log.d("test", "todo add details")
                    }


                }

            }
            binding.spendParentView.setOnLongClickListener {
                selectOrRemove(position)
                true
            }


        }

        private fun selectOrRemove(position: Int) {
            if (!spendOptions.isPresent(position)) {
                spendOptions.addSpend(position)
            } else {
                spendOptions.removeSpend(position)
            }
        }
    }


}