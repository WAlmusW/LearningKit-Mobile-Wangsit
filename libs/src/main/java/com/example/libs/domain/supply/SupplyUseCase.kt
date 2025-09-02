package com.example.libs.domain.supply

import com.example.libs.data.repository.supply.SupplyRepository
import com.example.libs.data.source.network.model.request.supply.DeleteSuppliesBody
import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import com.example.libs.data.source.network.model.request.supply.PatchStatusSuppliesBody
import javax.inject.Inject

class  GetSuppliesUseCase @Inject constructor(
    private val repository: SupplyRepository
) {
    operator fun invoke(query: GetSuppliesQueryParams) = repository.getSupplies(query)
}

class DeleteSuppliesUseCase @Inject constructor(
    private val repository: SupplyRepository
) {
    operator fun invoke(body: DeleteSuppliesBody) = repository.deleteSupplies(body)
}

class PatchStatusSuppliesUseCase @Inject constructor(
    private val repository: SupplyRepository
) {
    operator fun invoke(body: PatchStatusSuppliesBody) = repository.patchStatusSupplies(body)
}