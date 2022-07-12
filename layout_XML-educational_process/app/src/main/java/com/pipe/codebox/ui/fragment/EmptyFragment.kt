package com.pipe.codebox.ui.fragment

import com.pipe.codebox.R
import com.pipe.codebox.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmptyFragment: BaseFragment() {

    override val layoutId: Int = R.layout.fragment_fill

    override fun handleSuccess(data: Any) {
    }


}