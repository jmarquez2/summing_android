package com.jrms.summing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jrms.summing.R
import com.jrms.summing.databinding.SpendItemBinding
import com.jrms.summing.models.Spend

class SpendAdapter(var list : List<Spend>) : RecyclerView.Adapter<SpendAdapter.SpendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendViewHolder {
        val binding = DataBindingUtil.inflate<SpendItemBinding>(LayoutInflater.from(parent.context), R.layout.spend_item, parent, false)
        return SpendViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: SpendViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun assignList(list : List<Spend>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SpendViewHolder(itemView: View, private val binding: SpendItemBinding) :
        RecyclerView.ViewHolder(itemView) {

            fun bind(spend: Spend){
                binding.spend = spend
            }
    }

}