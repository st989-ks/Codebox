package com.pipe.codebox.ui.fragment

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.pipe.codebox.R
import com.pipe.codebox.domain.entity.DataClass
import com.pipe.codebox.domain.entity.HomeValue
import com.pipe.codebox.domain.entity.HomeWork
import com.pipe.codebox.domain.entity.Lesson
import com.pipe.codebox.extensions.findClosest
import com.pipe.codebox.ui.adapter.GridSpacingItemDecoration
import com.pipe.codebox.presenter.view.HomeValueViewModel
import com.pipe.codebox.ui.adapter.HomeClassesAdapter
import com.pipe.codebox.ui.adapter.HomeWorkAdapter
import com.pipe.codebox.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.element_reminder.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: BaseFragment() {

    override val layoutId: Int = R.layout.fragment_home

    @Inject
    lateinit var homeClassesAdapter: HomeClassesAdapter

    @Inject
    lateinit var homeWorkAdapter: HomeWorkAdapter

    @Inject
    lateinit var homeValueViewModel: HomeValueViewModel

    private var position = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        initRecycler()
    }

    private fun initRecycler() {
        PagerSnapHelper().attachToRecyclerView(recycler_classes_card)
        recycler_classes_card.apply {
            layoutManager = GridLayoutManager(activity, 1,GridLayoutManager.HORIZONTAL, false)
            addItemDecoration(GridSpacingItemDecoration(1, 20, true))
        }

        recycler_classes_card.adapter = homeClassesAdapter
        recycler_homework_card.adapter = homeWorkAdapter
    }


    override fun handleSuccess(data: Any) {
        when(data){
            is HomeValue ->{
                setExamTime(data.timeExam.date.toList())
                setRecyclerClasses(data.lessonsHome)
                setRecyclerHomeWork(data.homeWorks)
            }
        }

    }

    private fun setRecyclerHomeWork(homeWorks: List<HomeWork>) {
        homeWorkAdapter.clearAll()
        homeWorkAdapter.addAll(homeWorks as MutableList<HomeWork>)
    }

    private fun setRecyclerClasses(lessonsHome: List<Lesson>) {
        setNumPosition(lessonsHome)
        value_classes_today.text = getString(R.string.classes_today_for_fragment, lessonsHome.size)
        homeClassesAdapter.clearAll()
        homeClassesAdapter.addAll(lessonsHome as MutableList<Lesson>)
        recycler_classes_card.layoutManager?.scrollToPosition(position)
    }

    private fun setExamTime(chars: List<Char>) {
        if (chars.size>5){
            event_date_one_text.text = chars[0].toString()
            event_date_two_text.text = chars[1].toString()
            event_date_three_text.text = chars[2].toString()
            event_date_four_text.text = chars[3].toString()
            event_date_five_text.text = chars[4].toString()
            event_date_six_text.text = chars[5].toString()
        }
    }

    private fun observer() {
        homeValueViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        homeValueViewModel.requestVM()
    }

    private fun setNumPosition(data: List<Lesson>) {
        val listHour = mutableListOf<Int>()
        var hour = 0
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

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDateHour(): Int {
        val sdf = SimpleDateFormat("H")
        return sdf.format(Date()).toInt()
    }
}