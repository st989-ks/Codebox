package com.pipe.codebox.data.request

import com.pipe.codebox.domain.repository.ClassesRepository
import com.pipe.codebox.domain.repository.HomeValueRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun bindHomeValueDataSource(
    ): HomeValueRepository =
        HomeValueRepositoryImpl()

    @Provides
    fun bindClassesDataSource(
    ): ClassesRepository =
        ClassesRepositoryImpl()
}