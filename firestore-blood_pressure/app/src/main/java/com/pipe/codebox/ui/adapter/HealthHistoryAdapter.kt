package com.pipe.codebox.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.databinding.ElementHistoryDataBinding
import com.pipe.codebox.extensions.ColorEnum
import com.pipe.codebox.extensions.getColorInt
import java.util.function.Predicate
import javax.inject.Inject

class HealthHistoryAdapter @Inject constructor() :
    RecyclerView.Adapter<HealthHistoryAdapter.HealthHistoryDataViewHolder>() {


    private var dataAdapter: MutableList<HealthData> = mutableListOf()

    private var onContainerClickListener: ((HealthData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HealthHistoryDataViewHolder(
        ElementHistoryDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = if (dataAdapter.size > 0) dataAdapter.size else 0

    override fun onBindViewHolder(holder: HealthHistoryDataViewHolder, position: Int) {
        holder.bind(dataAdapter[position], position)
    }

    inner class HealthHistoryDataViewHolder(private val binding: ElementHistoryDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HealthData, position: Int) = with(binding) {

            if (position > 0 && data.date != dataAdapter[position - 1].date) groupDate.visibility =
                View.VISIBLE

            if (position == 0) groupDate.visibility = View.VISIBLE

            groupData.let {
                val gradient = GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
                        Color.TRANSPARENT,
                        getDataColor(data.upperBloodPressure, root.context), Color.TRANSPARENT
                    )
                )
                it.background = gradient
            }
            tvDate.text = data.date
            tvTime.text = data.time
            tvUpPressure.text = data.upperBloodPressure
            tvLowPressure.text = data.lowerBloodPressure
            tvPulse.text = data.pulse
            executePendingBindings()
            itemView.setOnLongClickListener {
                onContainerClickListener?.let { it(data) }
                true
            }
        }
    }

    fun setOnLongClick(listener: (HealthData) -> Unit) {
        onContainerClickListener = listener
    }

    private fun getDataColor(upperBloodPressure: String, context: Context): Int {
        return try {
            when (upperBloodPressure.toInt()) {
                in 0..99 -> ColorEnum.BLUE.getColorInt(context)
                in 100..119 -> ColorEnum.DARK_GREEN.getColorInt(context)
                in 120..129 -> ColorEnum.GREEN.getColorInt(context)
                in 130..139 -> ColorEnum.LIGHT_GREEN.getColorInt(context)
                in 140..159 -> ColorEnum.YELLOW.getColorInt(context)
                in 160..179 -> ColorEnum.ORANGE.getColorInt(context)
                in 180..1000 -> ColorEnum.RED.getColorInt(context)
                else -> Color.WHITE
            }
        } catch (e: Throwable) {
            println(e.message)
            Color.WHITE
        }
    }

    fun addAll(dataIt: MutableList<HealthData>) {
        for (data in dataIt) {
            add(data)
        }
    }

    fun add(data: HealthData) {
        dataAdapter.add(data)
        notifyItemInserted(dataAdapter.size - 1)
    }

    fun clearAll() {
        dataAdapter.clear()
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeData(id: Long) {
        val healthDataRemove = Predicate { healthData: HealthData -> healthData.id == id }
        dataAdapter.removeIf(healthDataRemove)
        notifyDataSetChanged()
    }

}