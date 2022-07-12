package com.pipe.codebox.ui.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.R
import com.pipe.codebox.databinding.ProgressBarBinding
import com.pipe.codebox.databinding.RecyclerElementBinding
import com.pipe.codebox.domain.entity.DataClass
import javax.inject.Inject

class ClassesAdapter @Inject constructor(
    private val application: Application
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindind: RecyclerElementBinding
    private var onContainerClickListener: ((DataClass) -> Unit)? = null
    private var retryPageLoadClickListener: (Unit)? = null
    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false
    private var errorMsg: String? = ""
    private val item: Int = 0
    private var hourInPosition: Int = 1
    private val loading: Int = 1

    private var models: MutableList<DataClass> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == item) {
            bindind = RecyclerElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            DataClassViewHolder(bindind)
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
        val model = models[position]

        if (getItemViewType(position) == item) {
            val locationOrderVH: DataClassViewHolder = holder as DataClassViewHolder
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
            if (position == models.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }

    override fun getItemCount() = if (models.size > 0) models.size else 0

    class ProgressViewHolder(binding: ProgressBarBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ProgressBarBinding = binding
    }

    inner class DataClassViewHolder(binding: RecyclerElementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: RecyclerElementBinding = binding

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(
            value: DataClass,
        ) {
            val hour = getHour(value.scheduler)
            if (hourInPosition == hour) {
                itemRowBinding.circleLittle.visibility = View.INVISIBLE
                itemRowBinding.circleBig.visibility = View.VISIBLE
            }

            itemRowBinding.timeWhen.text = value.scheduler

            if (value.greenBackground) {
                itemRowBinding.cardClasses.classesCardView.visibility = View.GONE
                itemRowBinding.cardEducation.cardEducationView.visibility = View.VISIBLE
                itemRowBinding.cardEducation.imageEducation.setImageDrawable(
                    application.getDrawable(value.icon)
                )
                itemRowBinding.cardEducation.titleEducation.text = value.title
                itemRowBinding.cardEducation.educationTeacher.text = value.teacher
                itemRowBinding.cardEducation.educationExplanation.text = value.description
                itemRowBinding.cardEducation.cardEducationView.setOnClickListener {
                    onContainerClickListener?.let { it(value) }
                }

            } else {
                itemRowBinding.cardClasses.classesCardView.visibility = View.VISIBLE
                itemRowBinding.cardEducation.cardEducationView.visibility = View.GONE

                if (value.video) {
                    itemRowBinding.cardClasses.blueView.visibility = View.VISIBLE
                    itemRowBinding.cardClasses.blueView.setOnClickListener {
                        onContainerClickListener?.let { it(value) }
                    }
                }
                itemRowBinding.cardClasses.cardImage.setImageDrawable(application.getDrawable(value.icon))
                itemRowBinding.cardClasses.titleClass.text = value.title
                itemRowBinding.cardClasses.watchDecryption.text = value.teacher
            }
        }
    }

    fun setOnContainerClickListener(listener: (DataClass) -> Unit) {
        onContainerClickListener = listener
    }

    fun setRetryPageLoadClickListener(boolean: () -> Unit) {
        retryPageLoadClickListener
    }

    private fun getHour(value: String): Int {
        val first = value[0].toString().toIntOrNull() ?: ""
        val second = value[1].toString().toIntOrNull() ?: ""
        return "$first$second".toInt()
    }

    fun addAll(values: MutableList<DataClass>, hour: Int) {
        hourInPosition = hour
        for (value in values) {
            add(value)
        }
    }

    fun showRetry(show: Boolean, errorMsg: String) {
        retryPageLoad = show
        notifyItemChanged(models.size - 1)
        this.errorMsg = errorMsg
    }

    fun clearAll() {
        models.clear()
        notifyDataSetChanged()
    }

    fun add(value: DataClass) {
        models.add(value)
        notifyItemInserted(models.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(
            DataClass(
                title = "Load",
                teacher = "",
                icon = R.drawable.ic_load,
                scheduler = "",
                description = "",
                bigCircle = false,
                greenBackground = false,
                video = false
            )
        )
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = models.size - 1
        val value: DataClass = models[position]
        if (value != null) {
            models.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}