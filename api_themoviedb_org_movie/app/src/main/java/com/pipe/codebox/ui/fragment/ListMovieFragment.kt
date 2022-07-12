package com.pipe.codebox.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.pipe.codebox.R
import com.pipe.codebox.domain.entity.MovieEntity
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.extensions.BUNDLE_ID
import com.pipe.codebox.extensions.isInternetAvailable
import com.pipe.codebox.extensions.recyclerAdapter.GridSpacingItemDecoration
import com.pipe.codebox.presenter.view.GetListMovieViewModel
import com.pipe.codebox.ui.adapter.ListMovieAdapter
import com.pipe.codebox.ui.adapter.PaginationScrollListener
import com.pipe.codebox.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_movie.*
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@AndroidEntryPoint
class ListMovieFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_list_movie
    private var currentPage = 1

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    @Inject
    lateinit var listMovieAdapter: ListMovieAdapter

    @Inject
    lateinit var getListMovieViewModel: GetListMovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        initRecycler()
        initButton()
        initSearch()
    }

    private fun initRecycler() {
        recycler_movies.apply {
            layoutManager = GridLayoutManager(activity, 2)
            addItemDecoration(GridSpacingItemDecoration(2, 30, false))
            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as GridLayoutManager) {
                override fun loadMoreItems() {
                    isLoading = true
                    currentPage += 1
                    loadNextPage()
                }
                override fun isLastPage() = isLastPage
                override fun isLoading() = isLoading
            })
        }
        recycler_movies.adapter = listMovieAdapter
    }

    private fun loadNextPage() {
        if (requireActivity().isInternetAvailable()) {
            getListMovieViewModel.requestMovies(page = currentPage)
        } else {
            listMovieAdapter.showRetry(true, fetchErrorMessage(null))
        }
    }

    private fun initButton() {
        listMovieAdapter.setOnContainerClickListener { it ->
            bundle.putInt(BUNDLE_ID, it.Id)
            navigatorModule.navigate(R.id.movieDetail, bundle)
        }
        listMovieAdapter.setRetryPageLoadClickListener { loadNextPage()  }
    }

    private fun observer() {
        getListMovieViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        getListMovieViewModel.requestMovies()
    }

    override fun handleSuccess(data: Any) {
        when (data) {
            is List<*> -> {
                val loc = data as MutableList<MovieEntity>
                if (isLoading) {
                    listMovieAdapter.removeLoadingFooter()
                    isLoading = false
                    listMovieAdapter.addAll(loc)
                } else {
                    listMovieAdapter.addAll(loc)
                }
            }
        }
    }

    private fun fetchErrorMessage(throwable: Throwable?): String {
        var errorMsg: String = resources.getString(R.string.error_msg_unknown)

        if (!requireActivity().isInternetAvailable()) {
            errorMsg = resources.getString(R.string.error_msg_no_internet)
        } else if (throwable is TimeoutException) {
            errorMsg = resources.getString(R.string.error_msg_timeout)
        }

        return errorMsg
    }

    private fun initSearch() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?)= true
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(APP_TAG, "onQueryTextChange: $newText")
                if (!newText.isNullOrEmpty()) {
                    listMovieAdapter.clearAll()
                    getListMovieViewModel.searchDebounced(newText)
                }
                return false
            }
        })
    }
}
