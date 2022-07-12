package com.pipe.codebox.data.request.movieList

import com.pipe.codebox.data.request.movieList.api.MovieListApi
import com.pipe.codebox.data.request.movieList.repository.GetListMovieRepositoryImpl
import com.pipe.codebox.domain.repository.GetListMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class GetListMovieModule {

    @Provides
    fun provideLoansListApi(
        retrofit: Retrofit,
    ): MovieListApi {
        return retrofit.create(MovieListApi::class.java)
    }

    @Provides
    fun bindLoansListDataSource(
        api: MovieListApi,
    ): GetListMovieRepository =
        GetListMovieRepositoryImpl(api)
}