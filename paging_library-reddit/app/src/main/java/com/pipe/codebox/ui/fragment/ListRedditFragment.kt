package com.pipe.codebox.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pipe.codebox.R
import com.pipe.codebox.presenter.GetListViewModel
import com.pipe.codebox.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_hot.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListRedditFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_list_hot

    @Inject
    lateinit var listRedditAdapter: ListRedditAdapter

    @Inject
    lateinit var getListViewModel: GetListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observer()
        initButton()
    }

    private fun initRecycler() {
        recycler_reddit_fragment.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = listRedditAdapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter(),
                footer = ReposLoadStateAdapter()
            )
        }
    }

    private fun initButton() {
        listRedditAdapter.setOnPostClickListener {
            Snackbar.make(fragment_list_hot_container, it.title, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun observer() {
        lifecycleScope.launch {
            getListViewModel.getFileDataStream().collectLatest {
                listRedditAdapter.submitData(it)
            }
        }
    }
}
