package com.example.libs.data.source.network.services

import com.example.libs.data.source.network.model.request.supply.DeleteSuppliesBody
import com.example.libs.data.source.network.model.response.supply.DeleteSuppliesResponse
import com.example.libs.data.source.network.model.response.supply.GetSuppliesResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.QueryMap

interface SupplyApi {
    @GET("/v2/supplies")
    suspend fun getSupplies(
        @Header("Authorization") token: String,
        @QueryMap query: Map<String, String?> = mapOf()
    ) : Response<GetSuppliesResponse>

    @HTTP(method = "DELETE", path = "/v2/supplies", hasBody = true)
    suspend fun deleteSupplies(
        @Header("Authorization") token: String,
        @Part body: DeleteSuppliesBody,
    ) : Response<DeleteSuppliesResponse>
}