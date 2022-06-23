package com.pipe.codebox.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.databinding.RecyclerPointsBinding
import com.pipe.codebox.domain.entity.Locations
import javax.inject.Inject

class LocationPointsAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindind: RecyclerPointsBinding

    var loc: List<Locations> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        bindind = RecyclerPointsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationPointsViewHolder(bindind)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LocationPointsViewHolder).bind(bindind, loc[position])
    }

    override fun getItemCount(): Int = loc.size

    inner class LocationPointsViewHolder(binding: RecyclerPointsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            binding: RecyclerPointsBinding,
            loc: Locations,
        ) {

            binding.textName.text = loc.name
            binding.cardAll.setOnClickListener { onContainerClickListener?.let { it(loc) } }
        }
    }

    private var onContainerClickListener: ((Locations) -> Unit)? = null
    fun setOnContainerClickListener(listener: (Locations) -> Unit) {
        onContainerClickListener = listener
    }
}