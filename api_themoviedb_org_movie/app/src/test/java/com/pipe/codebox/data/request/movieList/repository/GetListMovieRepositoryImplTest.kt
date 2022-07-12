package com.pipe.codebox.data.request.movieList.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.annotations.SerializedName
import com.pipe.codebox.MainDispatcherRule
import com.pipe.codebox.data.models.Movie
import com.pipe.codebox.data.models.MovieList
import com.pipe.codebox.data.request.movieList.api.MovieListApi
import com.pipe.codebox.domain.entity.MovieEntity
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class GetListMovieRepositoryImplTest {
    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var impl: GetListMovieRepositoryImpl

    @Mock
    private lateinit var api: MovieListApi


    private val anyString = "any"
    private val anyInt = 1
    private val anyIntString = "1"

    private val informMovie = Movie(
        anyString, anyString, listOf(), anyIntString, anyString, anyString, anyString,
        anyString, anyString, anyString, anyString, anyString, anyIntString, anyIntString
    )

    private val testMovieValue: MovieList =
        MovieList(anyInt, listOf<Movie>(informMovie, informMovie), anyInt, anyInt)

    private val informAny = MovieEntity(anyInt, anyInt, anyString, anyString, anyInt.toDouble())

    private val testValue: List<MovieEntity> = listOf(informAny, informAny, informAny)


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        impl = GetListMovieRepositoryImpl(api)
    }

    @Test
    fun `when execute return success response`() = runBlocking {
        val expected = BaseResult.Success(testValue)
        whenever(api.requestMovieList(any())).thenReturn(testMovieValue)
        whenever(api.requestMovieSearch(any(), any())).thenReturn(testMovieValue)
        impl.invokeResponse(anyString).collectLatest {
            assertEquals(
                expected.data[1].page,
                (it as BaseResult.Success<List<MovieEntity>>).data[1].page
            )
        }
    }
}