package com.pipe.codebox.data.request

import android.app.Application
import com.pipe.codebox.data.common.FirestoreDataProvider
import com.pipe.codebox.domain.repository.HealthDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HealthDataModule {

    @Provides
    fun provideFirestoreData(
        firestoreDataProvider: FirestoreDataProvider,
    ): DataProvider {
        return firestoreDataProvider
    }

    @Provides
    fun bindDataSource(
        dataProvider: DataProvider,
        application: Application
    ): HealthDataRepository =
        HealthDataRepositoryImpl(dataProvider,application)
}