package com.pipe.codebox.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pipe.codebox.MainDispatcherRule
import com.pipe.codebox.domain.entity.InformationAll
import com.pipe.codebox.domain.entity.InformationCast
import com.pipe.codebox.domain.entity.InformationMovie
import com.pipe.codebox.domain.entity.MovieEntity
import com.pipe.codebox.domain.repository.GetListMovieRepository
import com.pipe.codebox.domain.repository.InformationMovieRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetListMovieUseCaseTest{

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: GetListMovieUseCase

    @Mock
    private lateinit var repository: GetListMovieRepository

    private val anyString = "any"
    private val anyInt = 0

    private val informAny = MovieEntity(anyInt,anyInt,anyString,anyString,anyInt.toDouble())

    private val testValue:List<MovieEntity> = listOf(informAny,informAny,informAny)



    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetListMovieUseCase(repository)
    }

    @Test
    fun `when execute return success response`() = runBlocking {
        val flow = flow<BaseResult<List<MovieEntity>, Throwable>> {
            emit(BaseResult.Success(testValue))
        }
        whenever(repository.invokeResponse(anyString)).thenReturn(flow)
        val expected = BaseResult.Success(testValue)
        useCase.invokeRequest(anyString).collectLatest {
            assertEquals(expected, it)
        }
    }

    @Test
    fun `when execute return error response`() = runBlocking {
        val flow = flow<BaseResult<List<MovieEntity> , Throwable>> {
            emit(BaseResult.Errors(Throwable()))
        }
        whenever(repository.invokeResponse(anyString)).thenReturn(flow)
        val expected = BaseResult.Errors(Throwable())
        useCase.invokeRequest(anyString).collectLatest {
            assertEquals(expected.toString(), it.toString())
        }
    }
}