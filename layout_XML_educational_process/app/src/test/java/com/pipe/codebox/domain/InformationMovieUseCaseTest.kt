package com.pipe.codebox.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pipe.codebox.MainDispatcherRule
import com.pipe.codebox.domain.entity.InformationAll
import com.pipe.codebox.domain.entity.InformationCast
import com.pipe.codebox.domain.entity.InformationMovie
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

internal class InformationMovieUseCaseTest{

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: InformationMovieUseCase

    @Mock
    private lateinit var repository: InformationMovieRepository

    private val anyString = "any"
    private val anyInt = 0
    private val informAny = InformationCast(anyString, anyString)

    private val list: List<InformationCast> = listOf(informAny, informAny)

    private val testValue = InformationAll(
        movie = InformationMovie(
            anyString,
            anyString,
            anyInt.toDouble(),
            anyInt,
            anyString,
            anyString
        ),
        cast = list
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = InformationMovieUseCase(repository)
    }

    @Test
    fun `when execute return success response`() = runBlocking {
        val flow = flow<BaseResult<InformationAll, Throwable>> {
            emit(BaseResult.Success(testValue))
        }
        whenever(repository.invokeResponseInformation(anyString)).thenReturn(flow)
        val expected = BaseResult.Success(testValue)
        useCase.invokeInformation(anyString).collectLatest {
            assertEquals(expected, it)
        }
    }

    @Test
    fun `when execute return error response`() = runBlocking {
        val flow = flow<BaseResult<InformationAll, Throwable>> {
            emit(BaseResult.Errors(Throwable()))
        }
        whenever(repository.invokeResponseInformation(anyString)).thenReturn(flow)
        val expected = BaseResult.Errors(Throwable())
        useCase.invokeInformation(anyString).collectLatest {
            assertEquals(expected.toString(), it.toString())
        }
    }
}