package com.example.libs.data.repository.supply

import com.example.libs.base.Result
import com.example.libs.data.mapper.SupplyMapper
import com.example.libs.data.model.SupplyEntity
import com.example.libs.data.source.network.model.request.supply.DeleteSuppliesBody
import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import com.example.libs.data.source.network.model.request.supply.PatchStatusSuppliesBody
import com.example.libs.data.source.network.model.response.supply.GetSuppliesResponse
import com.example.libs.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeSupplyRepositoryImpl @Inject constructor(
    private val supplyMapper: SupplyMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SupplyRepository {

    override fun getSupplies(query: GetSuppliesQueryParams): Flow<Result<List<SupplyEntity>>> = flow<Result<List<SupplyEntity>>> {
        val mock = mockNetworkModels()

        emit(Result.Success(supplyMapper.mapSupplies(mock)))
    }.catch { e ->
        emit(Result.Error<List<SupplyEntity>>(e.message ?: "Unknown error"))
    }.flowOn(ioDispatcher)

    override fun deleteSupplies(body: DeleteSuppliesBody): Flow<Result<Int>> = flow<Result<Int>> {
        val mock = 5

        emit(Result.Success(mock))
    }.catch { e ->
        emit(Result.Error<Int>(e.message ?: "Unknown error"))
    }.flowOn(ioDispatcher)

    override fun patchStatusSupplies(body: PatchStatusSuppliesBody): Flow<Result<Any>> = flow<Result<Any>> {
        val mock = Any()

        emit(Result.Success(mock))
    }.catch { e ->
        emit(Result.Error<Any>(e.message ?: "Unknown error"))
    }.flowOn(ioDispatcher)


    private fun mockNetworkModels(): List<GetSuppliesResponse.Data.Data> = listOf(
        // 1) PT. ABC Indonesia — Active
        GetSuppliesResponse.Data.Data(
            id = "sup-001",
            status = "Active",
            companyName = "PT. ABC Indonesia",
            country = "Indonesia",
            state = "Jakarta Utara",
            item = listOf(
                GetSuppliesResponse.Data.Data.Item(
                    itemName = "Orderne-4731",
                    sku = listOf("SKU-123", "SKU-124")
                )
            ),
            picName = "Nakamoto Y",
            updatedAt = "2023-09-29T13:00:01Z"
        ),

        // 2) PT. Sinar Mas Dunia — Inactive
        GetSuppliesResponse.Data.Data(
            id = "sup-002",
            status = "Inactive",
            companyName = "PT. Sinar Mas Dunia",
            country = "Indonesia",
            state = "Jakarta Utara",
            item = listOf(
                GetSuppliesResponse.Data.Data.Item(
                    itemName = "Orderne-4731",
                    sku = listOf("SKU-200")
                )
            ),
            picName = "Mark L",
            updatedAt = "2023-09-29T13:00:01Z"
        ),

        // 3) PT. GHI Indonesia — Active (+3 more feel)
        GetSuppliesResponse.Data.Data(
            id = "sup-003",
            status = "Active",
            companyName = "PT. GHI Indonesia",
            country = "Indonesia",
            state = "Jakarta Utara",
            item = listOf(
                GetSuppliesResponse.Data.Data.Item(
                    itemName = "Orderne-4731",
                    sku = listOf("SKU-300", "SKU-301", "SKU-302", "SKU-303")
                )
            ),
            picName = "Karina Y",
            updatedAt = "2023-09-29T13:00:01Z"
        ),

        // 4) PT. Ichitan Indonesia — Inactive
        GetSuppliesResponse.Data.Data(
            id = "sup-004",
            status = "Inactive",
            companyName = "PT. Ichitan Indonesia",
            country = "Indonesia",
            state = "Jakarta Utara",
            item = listOf(
                GetSuppliesResponse.Data.Data.Item(
                    itemName = "Orderne-4731",
                    sku = listOf("SKU-400", "SKU-401")
                )
            ),
            picName = "Hong E",
            updatedAt = "2023-09-29T13:00:01Z"
        ),

        // 5) PT. Ichitan Indonesia — Inactive (second card)
        GetSuppliesResponse.Data.Data(
            id = "sup-005",
            status = "Inactive",
            companyName = "PT. Ichitan Indonesia",
            country = "Indonesia",
            state = "Jakarta Utara",
            item = listOf(
                GetSuppliesResponse.Data.Data.Item(
                    itemName = "Orderne-4731",
                    sku = listOf("SKU-500")
                )
            ),
            picName = "Hong E",
            updatedAt = "2023-09-29T13:00:01Z"
        )
    )
}
