package com.pipe.codebox.data.request.informationMovie.api

import com.pipe.codebox.data.models.Credits
import com.pipe.codebox.data.models.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface InformationMovieApi {

    @GET("movie/{movieId}")
    suspend fun requestInformationMovie(@Path("movieId") movieId: String): MovieDetail

    @GET("movie/{movieId}/credits")
    suspend fun requestInformationActor(@Path("movieId") movieId: String): Credits

}