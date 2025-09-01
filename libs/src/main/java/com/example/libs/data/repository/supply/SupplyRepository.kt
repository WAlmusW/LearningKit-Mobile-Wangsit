package com.example.libs.data.repository.supply

import com.example.libs.base.Result
import com.example.libs.data.model.SupplyEntity
import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import kotlinx.coroutines.flow.Flow

interface SupplyRepository {
    fun getSupplies(query: GetSuppliesQueryParams): Flow<Result<List<SupplyEntity>>>
}