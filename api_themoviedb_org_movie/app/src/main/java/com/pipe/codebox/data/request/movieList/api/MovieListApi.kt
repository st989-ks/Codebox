package com.pipe.codebox.data.request.movieList.api

import com.pipe.codebox.data.models.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListApi {

    @GET("trending/movie/day")
    suspend fun requestMovieList(@Query("page") page: Int): MovieList

    @GET("search/movie")
    suspend fun requestMovieSearch(@Query("query") query: String, @Query("page") page: Int): MovieList

}