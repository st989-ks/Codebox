package com.pipe.codebox.ui.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.R
import com.pipe.codebox.databinding.ElementClassesBinding
import com.pipe.codebox.databinding.ProgressBarBinding
import com.pipe.codebox.domain.entity.Lesson
import javax.inject.Inject

class HomeClassesAdapter @Inject constructor(
    private val application: Application
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindind: ElementClassesBinding
    private var onContainerClickListener: ((Lesson) -> Unit)? = null
    private var retryPageLoadClickListener: (Unit)? = null
    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false
    private var errorMsg: String? = ""
    private val item: Int = 0
    private val loading: Int = 1

    private var lessonModels: MutableList<Lesson> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == item) {
            bindind = ElementClassesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            LessonViewHolder(bindind)
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
        val model = lessonModels[position]

        if (getItemViewType(position) == item) {
            val locationOrderVH: LessonViewHolder = holder as LessonViewHolder
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
            if (position == lessonModels.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }

    override fun getItemCount() = if (lessonModels.size > 0) lessonModels.size else 0

    class ProgressViewHolder(binding: ProgressBarBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ProgressBarBinding = binding
    }

    inner class LessonViewHolder(binding: ElementClassesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ElementClassesBinding = binding

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(
            lesson: Lesson,
        ) {
            if (lesson.video) {
                itemRowBinding.blueView.visibility = View.VISIBLE
                itemRowBinding.blueView.setOnClickListener {
                    onContainerClickListener?.let { it(lesson) }
                }
            }

            if (lesson.greenBackground) {
                itemRowBinding.classesConstraintLayout.background =
                    application.getDrawable(R.drawable.green_gradient_background)
                itemRowBinding.cardImageClass.backgroundTintList =
                    application.getColorStateList(R.color.blue_green)
            }

            itemRowBinding.cardImage.setImageDrawable(application.getDrawable(lesson.icon))

            itemRowBinding.titleClass.text = lesson.title
            itemRowBinding.watchDecryption.text =
                application.getString(R.string.text_watch_for_adapter, lesson.scheduler)

        }
    }

    fun setOnContainerClickListener(listener: (Lesson) -> Unit) {
        onContainerClickListener = listener
    }

    fun setRetryPageLoadClickListener(boolean: () -> Unit) {
        retryPageLoadClickListener
    }

    fun addAll(lessons: MutableList<Lesson>) {
        for (lesson in lessons) {
            add(lesson)
        }
    }

    fun showRetry(show: Boolean, errorMsg: String) {
        retryPageLoad = show
        notifyItemChanged(lessonModels.size - 1)
        this.errorMsg = errorMsg
    }

    fun clearAll() {
        lessonModels.clear()
        notifyDataSetChanged()
    }

    fun add(lesson: Lesson) {
        lessonModels.add(lesson)
        notifyItemInserted(lessonModels.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(
            Lesson(
                title = "Load",
                icon = R.drawable.ic_load,
                scheduler = "",
                video = false,
                greenBackground = false
            )
        )
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = lessonModels.size - 1
        val lesson: Lesson = lessonModels[position]
        if (lesson != null) {
            lessonModels.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}