package com.pipe.codebox.data.request

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pipe.codebox.data.mappers.toPostEntity
import com.pipe.codebox.domain.entity.PostEntity
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.extensions.PAGE_SIZE
import com.pipe.codebox.extensions.SharedPrefs
import javax.inject.Inject

class RedditListPagingSource @Inject constructor(
    private val redditApi: RedditApi,
    private val sharedPrefs: SharedPrefs
) : PagingSource<Int, PostEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostEntity> {
        Log.i(APP_TAG, sharedPrefs.getAfter())
        try {
            val position = params.key ?: 1
            val response = redditApi.getData(sharedPrefs.getAfter(), PAGE_SIZE)
            sharedPrefs.setAfter(response.data.after)
            return LoadResult.Page(
                data = response.data.children.toPostEntity(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (throwable: Throwable) {
            val errorThrowable = Throwable(
                "Something Went Wrong Please Try again later.",
                Throwable("Processing Error")
            )
            return LoadResult.Error(errorThrowable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}