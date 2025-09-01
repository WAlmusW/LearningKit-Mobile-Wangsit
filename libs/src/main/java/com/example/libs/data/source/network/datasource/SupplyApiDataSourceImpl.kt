package com.example.libs.data.source.network.datasource

import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import com.example.libs.data.source.network.model.response.supply.GetSuppliesResponse
import com.example.libs.data.source.network.services.SupplyApi
import com.example.libs.util.Util
import retrofit2.Response

class SupplyApiDataSourceImpl(
    private val supplyApi: SupplyApi,
): SupplyApiDataSource {
    override suspend fun getSupplies(
        token: String,
        query: GetSuppliesQueryParams
    ): Response<GetSuppliesResponse> {
        return try {
            supplyApi.getSupplies(token, query.toQueryMap())
        } catch (e: Exception) {
            Util.handleApiError(e)
        }
    }
}