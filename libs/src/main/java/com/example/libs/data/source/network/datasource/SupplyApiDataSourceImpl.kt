package com.example.libs.data.source.network.datasource

import com.example.libs.data.source.network.model.request.supply.DeleteSuppliesBody
import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import com.example.libs.data.source.network.model.request.supply.PatchStatusSuppliesBody
import com.example.libs.data.source.network.model.response.supply.DeleteSuppliesResponse
import com.example.libs.data.source.network.model.response.supply.GetSuppliesResponse
import com.example.libs.data.source.network.model.response.supply.PatchStatusSuppliesResponse
import com.example.libs.data.source.network.services.SupplyApi
import com.example.libs.util.Util
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.RequestBody
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

    override suspend fun deleteSupplies(
        token: String,
        body: DeleteSuppliesBody
    ): Response<DeleteSuppliesResponse> {
        return try {
            supplyApi.deleteSupplies(token, body)
        } catch (e: Exception) {
            Util.handleApiError(e)
        }
    }

    override suspend fun patchStatusSupplies(
        token: String,
        body: PatchStatusSuppliesBody
    ): Response<PatchStatusSuppliesResponse> {
        return try {
            supplyApi.patchStatusSupplies(token, body)
        } catch (e: Exception) {
            Util.handleApiError(e)
        }
    }
}