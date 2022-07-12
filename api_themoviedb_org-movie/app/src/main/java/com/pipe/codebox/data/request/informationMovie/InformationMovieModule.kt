package com.pipe.codebox.data.request.informationMovie

import com.pipe.codebox.data.request.informationMovie.api.InformationMovieApi
import com.pipe.codebox.data.request.informationMovie.repository.InformationMovieRepositoryImpl
import com.pipe.codebox.data.request.movieList.api.MovieListApi
import com.pipe.codebox.domain.repository.InformationMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class InformationMovieModule {

    @Provides
    fun provideLoansListApi(
        retrofit: Retrofit,
    ): InformationMovieApi {
        return retrofit.create(InformationMovieApi::class.java)
    }

    @Provides
    fun bindLoansListDataSource(
        api: InformationMovieApi,
    ): InformationMovieRepository =
        InformationMovieRepositoryImpl(api)
}