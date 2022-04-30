package com.example.catfacts.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.data.model.CatFact
import com.example.catfacts.databinding.CatFactBinding

class PagingCatFactAdapter : PagingDataAdapter<CatFact, PagingCatFactAdapter.CatFactViewHolder>(DiffCallback) {

    class CatFactViewHolder(val binding: CatFactBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CatFactViewHolder(
            CatFactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    //Binds data to a viewholder
    override fun onBindViewHolder(holder: CatFactViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding.catFact.text = item.fact
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<CatFact>() {
        override fun areItemsTheSame(old: CatFact, new: CatFact) = old == new
        override fun areContentsTheSame(old: CatFact, new: CatFact) = old.fact == new.fact
    }

}