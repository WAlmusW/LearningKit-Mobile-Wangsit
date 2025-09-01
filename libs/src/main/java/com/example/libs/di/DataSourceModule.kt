package com.example.libs.di

import com.example.libs.data.source.network.datasource.SupplyApiDataSource
import com.example.libs.data.source.network.datasource.SupplyApiDataSourceImpl
import com.example.libs.data.source.network.services.SupplyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideSupplyApiDataSource(supplyApi: SupplyApi): SupplyApiDataSource {
        return SupplyApiDataSourceImpl(supplyApi = supplyApi)
    }
}