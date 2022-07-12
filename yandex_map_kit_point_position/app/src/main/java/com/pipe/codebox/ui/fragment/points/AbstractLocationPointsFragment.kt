package com.pipe.codebox.ui.fragment.points

import com.pipe.codebox.presenter.view.GUDLocationPointsViewModel
import com.pipe.codebox.ui.adapter.LocationPointsAdapter
import com.pipe.codebox.ui.fragment.base.BaseFragment
import javax.inject.Inject

abstract class AbstractLocationPointsFragment : BaseFragment() {

    @Inject
    lateinit var gudLocationPointsViewModel: GUDLocationPointsViewModel

    @Inject
    lateinit var locationPointsAdapter: LocationPointsAdapter

}