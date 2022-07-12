package com.pipe.codebox.ui.fragment

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import com.pipe.codebox.R
import com.pipe.codebox.domain.entity.DataClass
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.extensions.findClosest
import com.pipe.codebox.presenter.view.ClassesViewModel
import com.pipe.codebox.ui.adapter.ClassesAdapter
import com.pipe.codebox.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_classes.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ClassesFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_classes

    @Inject
    lateinit var classesAdapter: ClassesAdapter

    @Inject
    lateinit var classesViewModel: ClassesViewModel

    private var hour = 1
    private var position = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        initRecycler()
        getDate()
    }

    private fun getDate() {
        date_today.text = getString(R.string.today_for_fragment, getCurrentDate())
    }

    private fun observer() {
        classesViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        classesViewModel.request()
    }

    private fun initRecycler() {
        recycler_classes_education_card.adapter = classesAdapter
    }

    override fun handleSuccess(data: Any) {
        when (data) {
            is List<*> -> {
                setRecycler(data as MutableList<DataClass>)
            }
        }
    }

    private fun setRecycler(data: MutableList<DataClass>) {
        setNumPosition(data)
        classesAdapter.clearAll()
        classesAdapter.addAll(data, hour)
        scrollToList()

    }

    private fun setNumPosition(data: MutableList<DataClass>) {
        val listHour = mutableListOf<Int>()
        data.forEach {
            val first = it.scheduler[0].toString().toIntOrNull() ?: ""
            val second = it.scheduler[1].toString().toIntOrNull() ?: ""
            listHour.add("$first$second".toInt())
        }
        if (listHour.isNotEmpty()) {
            hour = listHour.findClosest(getCurrentDateHour())!!
        }
        position = listHour.indexOf(hour)
    }

    private fun scrollToList() {
        recycler_classes_education_card.layoutManager?.scrollToPosition(position)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("d MMMM")
        return sdf.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDateHour(): Int {
        val sdf = SimpleDateFormat("H")
        return sdf.format(Date()).toInt()
    }
}