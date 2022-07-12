package com.pipe.codebox.data.request

import androidx.paging.ExperimentalPagingApi
import com.pipe.codebox.domain.repository.GetListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class GetListModule {

    @Provides
    fun provideListApi(
        retrofit: Retrofit,
    ): RedditApi {
        return retrofit.create(RedditApi::class.java)
    }

    @ExperimentalPagingApi
    @Provides
    fun bindListDataSource(
        redditListPagingSource: RedditListPagingSource
    ): GetListRepository =
        GetListRepositoryImpl(redditListPagingSource)
}