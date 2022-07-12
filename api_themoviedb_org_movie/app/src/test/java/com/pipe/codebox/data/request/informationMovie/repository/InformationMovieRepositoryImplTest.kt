package com.pipe.codebox.data.request.informationMovie.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.annotations.SerializedName
import com.pipe.codebox.MainDispatcherRule
import com.pipe.codebox.data.models.*
import com.pipe.codebox.data.request.informationMovie.api.InformationMovieApi
import com.pipe.codebox.data.request.movieList.api.MovieListApi
import com.pipe.codebox.data.request.movieList.repository.GetListMovieRepositoryImpl
import com.pipe.codebox.domain.entity.InformationAll
import com.pipe.codebox.domain.entity.InformationMovie
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
import org.mockito.kotlin.whenever

class InformationMovieRepositoryImplTest {
    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var impl: InformationMovieRepositoryImpl

    @Mock
    private lateinit var api: InformationMovieApi


    private val anyString = "any"
    private val anyInt = 1

    private val testMovieDetail: MovieDetail = MovieDetail(
        true,
        anyString,
        BelongsToCollection(anyString, anyInt, anyString, anyString),
        anyInt,
        listOf(),
        anyString,
        anyInt,
        anyString,
        anyString,
        anyString,
        anyString,
        anyInt.toDouble(),
        anyString,
        listOf(),
        listOf(),
        anyString,
        anyInt,
        anyInt,
        listOf(),
        anyString,
        anyString,
        anyString,
        true,
        anyInt.toDouble(),
        anyInt,
    )
    private val testCredits: Credits = Credits(listOf(), listOf(), anyInt)

    private val testValue = InformationAll(
        movie = InformationMovie(
            anyString,
            anyString,
            anyInt.toDouble(),
            anyInt,
            anyString,
            anyString
        ),
        cast = listOf()
    )


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        impl = InformationMovieRepositoryImpl(api)
    }

    @Test
    fun `when execute return success response`() = runBlocking {
        val expected = BaseResult.Success(testValue)
        whenever(api.requestInformationMovie(anyString)).thenReturn(testMovieDetail)
        whenever(api.requestInformationActor(anyString)).thenReturn(testCredits)
        impl.invokeResponseInformation(anyString).collectLatest {
            assertEquals(
                expected.data.movie.originalLanguage,
                (it as BaseResult.Success<InformationAll>).data.movie.originalLanguage)
        }
    }
}