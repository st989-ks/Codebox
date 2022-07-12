package com.pipe.codebox.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.pipe.codebox.R
import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.domain.entity.ListHealthData
import com.pipe.codebox.extensions.showToast
import com.pipe.codebox.presenter.view.HealthHistoryViewModel
import com.pipe.codebox.ui.adapter.HealthHistoryAdapter
import com.pipe.codebox.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history_data.view.*
import javax.inject.Inject

@AndroidEntryPoint
class HealthHistoryFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_history_data

    @Inject
    lateinit var healthHistoryAdapter: HealthHistoryAdapter

    @Inject
    lateinit var healthHistoryViewModel: HealthHistoryViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        initRecycler()
        initButton()
    }

    private fun initRecycler() {
        binding.recycler_movies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = healthHistoryAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initButton() {
        binding.fab.setOnClickListener { openDialog() }
        healthHistoryAdapter.setOnLongClick {
            healthHistoryViewModel.deleteData(it.id)
            healthHistoryAdapter.removeData(it.id)
        }
    }

    private fun observer() {
        healthHistoryViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        healthHistoryViewModel.requestData()
    }

    override fun handleShow(message: String){
        binding.constraint_fragment_history_data.showToast(message)
    }

    override fun handleSuccess(data: Any) {
        when (data) {
            is ListHealthData -> {
                healthHistoryAdapter.clearAll()
                healthHistoryAdapter.addAll(data.data as MutableList<HealthData>)
            }
            is HealthData->{
                healthHistoryAdapter.add(data)
            }
        }
    }

    private fun openDialog() {
        SaveDialog(requireActivity()).apply {
            show()
            onSave = { data ->
                dismiss()
                healthHistoryViewModel.saveData(data)
            }
            onCancel = { dismiss() }
        }
    }
}
