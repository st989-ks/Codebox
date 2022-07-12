package com.pipe.codebox.ui.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.R
import com.pipe.codebox.databinding.RecyclerPointsBinding
import com.pipe.codebox.domain.entity.Locations
import javax.inject.Inject

class LocationPointsAdapter @Inject constructor(
    private val application: Application
) :
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
        (holder as LocationPointsViewHolder).bind(bindind, loc[position], application)
    }

    override fun getItemCount(): Int = loc.size

    inner class LocationPointsViewHolder(binding: RecyclerPointsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(
            binding: RecyclerPointsBinding,
            loc: Locations,
            application: Application
        ) {

            binding.location.setImageDrawable(application.getDrawable(loc.markerId))
            binding.textName.text = loc.name
            binding.point.text = application.getString(R.string.text_point_for_fragment)
                .format(loc.point.latitude.toString(),loc.point.longitude.toString())

            binding.cardAll.setOnClickListener { onContainerClickListener?.let { it(loc) } }
            binding.cardAll.setOnLongClickListener {
                onContainerLongClickListener?.let { it(loc) }
                return@setOnLongClickListener true
            }
        }
    }

    private var onContainerClickListener: ((Locations) -> Unit)? = null

    fun setOnContainerClickListener(listener: (Locations) -> Unit) {
        onContainerClickListener = listener
    }

    private var onContainerLongClickListener: ((Locations) -> Unit)? = null

    fun setOnContainerLongClickListener(listener: (Locations) -> Unit) {
        onContainerLongClickListener = listener
    }
}