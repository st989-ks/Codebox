package com.pipe.codebox.presenter.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pipe.codebox.MainDispatcherRule
import com.pipe.codebox.domain.GetListMovieUseCase
import com.pipe.codebox.domain.InformationMovieUseCase
import com.pipe.codebox.domain.entity.InformationAll
import com.pipe.codebox.domain.entity.InformationCast
import com.pipe.codebox.domain.entity.InformationMovie
import com.pipe.codebox.domain.entity.MovieEntity
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.presenter.base.State
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class GetListMovieViewModelTest{

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var useCase: GetListMovieUseCase

    @Mock
    private lateinit var viewModel: GetListMovieViewModel

    private val anyString = "any"
    private val anyInt = 0

    private val informAny = MovieEntity(anyInt,anyInt,anyString,anyString,anyInt.toDouble())

    private val testValue:List<MovieEntity> = listOf(informAny,informAny,informAny)


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = GetListMovieViewModel(useCase)
    }

    @Test
    fun `init State result State Init`() = runBlocking {
        val actualInit = State.Init
        val expected = viewModel.state.value
        assertEquals(actualInit, expected)
    }

    @Test
    fun `when start return IsLoading(true)`() = runBlocking {
        val flow = flow<BaseResult<List<MovieEntity>, Throwable>> {
            BaseResult.Success(testValue) }
        whenever(useCase.invokeRequest()).thenReturn(flow)
        viewModel.requestMovies()
        val actual = viewModel.state.value
        val expected = State.IsLoading(true)
        assertEquals(expected, actual)
    }



    @Test
    fun `when  return result Success(value)`() = runBlocking {
        val flow = flow<BaseResult<List<MovieEntity>, Throwable>> {
            emit(BaseResult.Success(testValue))
        }
        whenever(useCase.invokeRequest()).thenReturn(flow)
        viewModel.requestMovies()
        val actual = viewModel.state.value
        val expected = State.Success(testValue)
        assertEquals(expected, actual)
    }

    @Test
    fun `when return Error`() = runBlocking {
        val flow = flow<BaseResult<List<MovieEntity>, Throwable>> {
            emit(BaseResult.Errors(Throwable("Errors")))
        }
        flow.collect {
            println(it)
        }
        whenever(useCase.invokeRequest(anyString)).thenReturn(flow)
        viewModel.requestMovies(anyString)
        val actual = viewModel.state.value.toString()
        val expected = State.Error(Throwable("Errors")).toString()
        assertEquals(expected, actual)
    }
}
