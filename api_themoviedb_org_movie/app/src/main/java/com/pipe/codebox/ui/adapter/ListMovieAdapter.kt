package com.pipe.codebox.ui.adapter

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.R
import com.pipe.codebox.databinding.ProgressBarBinding
import com.pipe.codebox.databinding.RecyclerMovieBinding
import com.pipe.codebox.domain.entity.MovieEntity
import com.pipe.codebox.extensions.APP_TAG
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ListMovieAdapter @Inject constructor(
    private val application: Application
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindind: RecyclerMovieBinding
    private var onContainerClickListener: ((MovieEntity) -> Unit)? = null
    private var retryPageLoadClickListener: (Unit)? = null
    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false
    private var errorMsg: String? = ""
    private val item: Int = 0
    private val loading: Int = 1

    private var moviesModels: MutableList<MovieEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == item) {
            bindind = RecyclerMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LocationPointsViewHolder(bindind)
        } else {
            val bindingLoad: ProgressBarBinding = ProgressBarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ProgressViewHolder(bindingLoad)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = moviesModels[position]
        if (getItemViewType(position) == item) {
            val locationOrderVH: LocationPointsViewHolder = holder as LocationPointsViewHolder
            locationOrderVH.bind(model)
        } else {
            val progressVH: ProgressViewHolder = holder as ProgressViewHolder
            if (retryPageLoad) {
                progressVH.itemRowBinding.loadingProgress.visibility = View.VISIBLE
                if (errorMsg != null) progressVH.itemRowBinding.loadmoreErrortxt.text = errorMsg
                else progressVH.itemRowBinding.loadmoreErrortxt.text =
                    application.getString(R.string.error_msg_unknown)

            } else {
                progressVH.itemRowBinding.loadingProgress.visibility = View.INVISIBLE
            }

            progressVH.itemRowBinding.loadMoreRetry.setOnClickListener {
                showRetry(false, "")
                retryPageLoadClickListener?.let { it }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            item
        } else {
            if (position == moviesModels.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }

    override fun getItemCount() = if (moviesModels.size > 0) moviesModels.size else 0

    class ProgressViewHolder(binding: ProgressBarBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ProgressBarBinding = binding
    }

    inner class LocationPointsViewHolder(binding: RecyclerMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: RecyclerMovieBinding = binding
        fun bind(loc: MovieEntity) = with(itemRowBinding) {
                posterRecycler.apply {
                    Log.i(APP_TAG, loc.posterPath)
                    if (loc.posterPath.isNullOrEmpty()) {
                        this.setImageResource(R.drawable.notimgicon)
                    } else {
                        Picasso.get().load(loc.posterPath).into(this)
                    }
                }
                voteCountRating.rating = loc.voteAverage.toFloat()
                cardMovie.setOnClickListener { onContainerClickListener?.let { it(loc) } }
                movieProgress.visibility = View.INVISIBLE
                linearLayout2.visibility = View.VISIBLE
                posterRecycler.visibility = View.VISIBLE
        }
    }

    fun setRetryPageLoadClickListener(boolean:()-> Unit) {
        retryPageLoadClickListener
    }

    fun setOnContainerClickListener(listener: (MovieEntity) -> Unit) {
        onContainerClickListener = listener
    }

    fun showRetry(show: Boolean, errorMsg: String) {
        retryPageLoad = show
        notifyItemChanged(moviesModels.size - 1)
        this.errorMsg = errorMsg
    }

    fun addAll(movies: MutableList<MovieEntity>) {
        for (movie in movies) {
            add(movie)
        }
    }

    fun clearAll() {
        moviesModels.clear()
        notifyDataSetChanged()
    }

    fun add(moive: MovieEntity) {
        moviesModels.add(moive)
        notifyItemInserted(moviesModels.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(MovieEntity(0, 0, "", "", 0.0))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position: Int = moviesModels.size - 1
        val movie: MovieEntity = moviesModels[position]

        if (movie != null) {
            moviesModels.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}