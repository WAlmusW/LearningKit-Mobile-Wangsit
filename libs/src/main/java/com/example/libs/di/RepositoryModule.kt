package com.example.libs.di

import com.example.libs.data.mapper.SupplyMapper
import com.example.libs.data.repository.supply.FakeSupplyRepositoryImpl
import com.example.libs.data.repository.supply.SupplyRepository
import com.example.libs.data.repository.supply.SupplyRepositoryImpl
import com.example.libs.data.source.network.datasource.SupplyApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSupplyRepository(
        supplyApiDataSource: SupplyApiDataSource,
        supplyMapper: SupplyMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SupplyRepository {
        return FakeSupplyRepositoryImpl(
            supplyMapper = supplyMapper,
            ioDispatcher = ioDispatcher,
        )
    }
}