package com.pipe.codebox.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.R
import com.pipe.codebox.databinding.RecyclerActorBinding
import com.pipe.codebox.domain.entity.InformationCast
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ListActorAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: RecyclerActorBinding

    var actors: List<InformationCast> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = RecyclerActorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationPointsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LocationPointsViewHolder).bind(binding, actors[position])
    }

    override fun getItemCount(): Int = actors.size

    inner class LocationPointsViewHolder(binding: RecyclerActorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            binding: RecyclerActorBinding,
            actors: InformationCast,
        ) {
            binding.profileRecycler.apply {
                if (actors.profilePath.isNullOrEmpty()) {
                    this.setImageResource(R.drawable.notimgicon)
                } else {
                    Picasso.get().load(actors.profilePath).into(this)
                }
            }
            binding.nameActor.text = actors.originalName
        }
    }

}