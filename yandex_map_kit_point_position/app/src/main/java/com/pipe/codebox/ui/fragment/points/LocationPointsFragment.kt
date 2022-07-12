package com.pipe.codebox.ui.fragment.points

import android.os.Bundle
import android.view.View
import com.pipe.codebox.R
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.ui.fragment.SavePointDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_location_points.*

@AndroidEntryPoint
class LocationPointsFragment : AbstractLocationPointsFragment() {

    override val layoutId: Int = R.layout.fragment_location_points

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initButton()
        gudLocationPointsViewModel.startGetList()
    }

    override fun handleSuccess(data: Any) {
        when (data) {
            is List<*> -> {
                val loc: List<Locations> = data as List<Locations>
                recycler_list.apply {
                    locationPointsAdapter.loc = loc
                    adapter = locationPointsAdapter
                }
            }
        }
    }

    private fun initButton() {
        locationPointsAdapter.setOnContainerClickListener { loc ->
            sharedPrefs.setPoint(loc.point)
            navigatorModule.navigateBack()
        }
        locationPointsAdapter.setOnContainerLongClickListener { loc ->
            openSavePointDialog(loc)
        }


    }

    private fun observe() {
        gudLocationPointsViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
    }


    private fun openSavePointDialog(location: Locations) {
        SavePointDialog(requireActivity(), location = location).apply {
            show()
            onSave = { loc ->
                dismiss()
                gudLocationPointsViewModel.startUpdate(loc)
            }
            onDelete = { key ->
                dismiss()
                gudLocationPointsViewModel.startDelete(key)
            }
        }
    }
}