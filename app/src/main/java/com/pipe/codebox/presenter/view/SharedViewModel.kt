package com.pipe.codebox.presenter.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pipe.codebox.domain.entity.Locations
import javax.inject.Inject


class SharedViewModel @Inject constructor() : ViewModel() {

    private val selected: MutableLiveData<Locations> = MutableLiveData<Locations>()

    fun select(item: Locations) {
        selected.value = item
    }

    fun getSelected(): LiveData<Locations>? {
        return selected
    }
}