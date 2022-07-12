package com.pipe.codebox.ui.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.R
import com.pipe.codebox.databinding.ElementHomeworkBinding
import com.pipe.codebox.databinding.ProgressBarBinding
import com.pipe.codebox.domain.entity.HomeWork
import javax.inject.Inject

class HomeWorkAdapter @Inject constructor(
    private val application: Application
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindind: ElementHomeworkBinding
    private var onContainerClickListener: ((HomeWork) -> Unit)? = null
    private var retryPageLoadClickListener: (Unit)? = null
    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false
    private var errorMsg: String? = ""
    private val item: Int = 0
    private val loading: Int = 1

    private var homeWorkModels: MutableList<HomeWork> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == item) {
            bindind = ElementHomeworkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            HomeWorkViewHolder(bindind)
        } else {
            val bindingLoad: ProgressBarBinding = ProgressBarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ProgressViewHolder(bindingLoad)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = homeWorkModels[position]

        if (getItemViewType(position) == item) {
            val locationOrderVH: HomeWorkViewHolder = holder as HomeWorkViewHolder
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
            if (position == homeWorkModels.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }

    override fun getItemCount() = if (homeWorkModels.size > 0) homeWorkModels.size else 0

    class ProgressViewHolder(binding: ProgressBarBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ProgressBarBinding = binding
    }

    inner class HomeWorkViewHolder(binding: ElementHomeworkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ElementHomeworkBinding = binding

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(
            homeWork: HomeWork,
        ) {
            itemRowBinding.titleHomework
            itemRowBinding.watchHomework.text =
                application.getString(R.string.days_left_for_adapter, homeWork.deadLine)

            if(homeWork.deadLine>3){
                itemRowBinding.watchHomework.setTextColor(application.getColor(R.color.color_text_watch))
            }

            itemRowBinding.imageHomework.setImageDrawable(application.getDrawable(homeWork.icon))

            itemRowBinding.explanationHomework.text = homeWork.work
            itemRowBinding.userImage1.setImageDrawable(application.getDrawable(homeWork.tagIconOne))
            itemRowBinding.userImage2.setImageDrawable(application.getDrawable(homeWork.tagIconTwo))

            itemRowBinding.homeworkCard.setOnClickListener {
                onContainerClickListener?.let { it(homeWork) }
            }

        }
    }

    fun setOnContainerClickListener(listener: (HomeWork) -> Unit) {
        onContainerClickListener = listener
    }

    fun setRetryPageLoadClickListener(boolean: () -> Unit) {
        retryPageLoadClickListener
    }

    fun addAll(homeWork: MutableList<HomeWork>) {
        for (value in homeWork) {
            add(value)
        }
    }

    fun showRetry(show: Boolean, errorMsg: String) {
        retryPageLoad = show
        notifyItemChanged(homeWorkModels.size - 1)
        this.errorMsg = errorMsg
    }

    fun clearAll() {
        homeWorkModels.clear()
        notifyDataSetChanged()
    }

    fun add(value: HomeWork) {
        homeWorkModels.add(value)
        notifyItemInserted(homeWorkModels.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(
            HomeWork(
                title = "Load",
                icon = R.drawable.ic_load,
                deadLine = 0,
                work = "",
                tagIconOne = R.drawable.ic_load,
                tagIconTwo = R.drawable.ic_load
            )
        )
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = homeWorkModels.size - 1
        val value: HomeWork = homeWorkModels[position]
        if (value != null) {
            homeWorkModels.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}