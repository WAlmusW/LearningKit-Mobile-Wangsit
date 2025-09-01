package com.example.libs.domain.supply

import com.example.libs.data.repository.supply.SupplyRepository
import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import javax.inject.Inject

class  GetSuppliesUseCase @Inject constructor(
    private val repository: SupplyRepository
) {
    operator fun invoke(query: GetSuppliesQueryParams) = repository.getSupplies(query)
}