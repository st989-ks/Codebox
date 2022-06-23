package com.pipe.codebox.data

import com.pipe.codebox.data.filelists.ActionFile
import com.pipe.codebox.data.repository.LocListRepositoryImpl
import com.pipe.codebox.domain.repository.LocRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class GetLocModule {

    @Provides
    fun bindGetLocDataSource(
        actionFile: ActionFile
    ): LocRepository =
        LocListRepositoryImpl(actionFile)
}