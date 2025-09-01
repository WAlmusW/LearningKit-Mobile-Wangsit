package com.example.libs.data.source.network.datasource

import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import com.example.libs.data.source.network.model.response.supply.GetSuppliesResponse
import retrofit2.Response

interface SupplyApiDataSource {
    suspend fun getSupplies(
        token: String,
        query: GetSuppliesQueryParams
    ): Response<GetSuppliesResponse>
}