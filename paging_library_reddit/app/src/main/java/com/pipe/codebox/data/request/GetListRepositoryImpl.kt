package com.pipe.codebox.data.request

import androidx.paging.*
import com.pipe.codebox.domain.entity.PostEntity
import com.pipe.codebox.domain.repository.GetListRepository
import com.pipe.codebox.extensions.PAGE_SIZE
import com.pipe.codebox.extensions.SharedPrefs
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListRepositoryImpl @Inject constructor(
    private val redditListPagingSource: RedditListPagingSource
) : GetListRepository {

    override fun invokeResponse(): Flow<PagingData<PostEntity>> {

        return Pager(
                config = PagingConfig( pageSize = PAGE_SIZE, enablePlaceholders = false ),
                pagingSourceFactory = { redditListPagingSource }).flow
    }
}

