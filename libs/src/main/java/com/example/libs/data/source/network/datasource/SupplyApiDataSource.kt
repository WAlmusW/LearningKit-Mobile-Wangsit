package com.example.libs.data.source.network.datasource

import com.example.libs.data.source.network.model.request.supply.DeleteSuppliesBody
import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import com.example.libs.data.source.network.model.request.supply.PatchStatusSuppliesBody
import com.example.libs.data.source.network.model.response.supply.DeleteSuppliesResponse
import com.example.libs.data.source.network.model.response.supply.GetSuppliesResponse
import com.example.libs.data.source.network.model.response.supply.PatchStatusSuppliesResponse
import retrofit2.Response

interface SupplyApiDataSource {
    suspend fun getSupplies(
        token: String,
        query: GetSuppliesQueryParams
    ): Response<GetSuppliesResponse>

    suspend fun deleteSupplies(
        token: String,
        body: DeleteSuppliesBody
    ): Response<DeleteSuppliesResponse>

    suspend fun patchStatusSupplies(
        token: String,
        body: PatchStatusSuppliesBody
    ): Response<PatchStatusSuppliesResponse>
}