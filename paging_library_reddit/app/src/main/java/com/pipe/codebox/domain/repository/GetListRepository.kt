package com.pipe.codebox.domain.repository

import androidx.paging.PagingData
import com.pipe.codebox.domain.entity.PostEntity
import kotlinx.coroutines.flow.Flow

interface GetListRepository {
    fun invokeResponse()
            : Flow<PagingData<PostEntity>>
}