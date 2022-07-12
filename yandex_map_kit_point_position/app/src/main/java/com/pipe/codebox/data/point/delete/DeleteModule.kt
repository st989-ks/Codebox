package com.pipe.codebox.data.point.delete

import com.pipe.codebox.domain.repository.DeleteLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DeleteModule {

    @Binds
    fun bindDeleteSource(
        impl: DeleteRepositoryImpl,
    ): DeleteLocationRepository
}