package com.tinto.waracletestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tinto.waracletestapp.databinding.CakeItemBinding
import com.tinto.waracletestapp.listeners.CakeItemClick
import com.tinto.waracletestapp.model.CakeDataModel


class CakesAdapter(private val itemList: List<CakeDataModel>, private val cakeItemClick : CakeItemClick) :
    RecyclerView.Adapter<CakesAdapter.CakesViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: CakeItemBinding = CakeItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return CakesViewHolder(itemBinding)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: CakesViewHolder, position: Int) {
        holder.bind(itemList[position], cakeItemClick = cakeItemClick)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return itemList.size
    }

    // Holds the views for adding the CakeDataModel
    class CakesViewHolder(
        private var binding: CakeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cakeDataModel: CakeDataModel, cakeItemClick : CakeItemClick) {
            binding.cakeModel = cakeDataModel
            binding.clickHandler = cakeItemClick
            binding.executePendingBindings()
        }
    }
}