package com.example.libs.data.source.network.services

import com.example.libs.data.source.network.model.response.supply.GetSuppliesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface SupplyApi {
    @GET("/v2/supplies")
    suspend fun getSupplies(
        @Header("Authorization") token: String,
        @QueryMap query: Map<String, String?> = mapOf()
    ) : Response<GetSuppliesResponse>
}