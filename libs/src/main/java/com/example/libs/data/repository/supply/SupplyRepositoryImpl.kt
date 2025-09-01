package com.example.libs.data.repository.supply

import com.example.libs.base.Result
import com.example.libs.data.mapper.SupplyMapper
import com.example.libs.data.model.SupplyEntity
import com.example.libs.data.source.network.datasource.SupplyApiDataSource
import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import com.example.libs.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SupplyRepositoryImpl @Inject constructor(
    private val supplyApiDataSource: SupplyApiDataSource,
    private val supplyMapper: SupplyMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): SupplyRepository {
    private val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NTY4NjcyNDcsImlkIjoiNjdjNTc3NjIwZmMwOWJmZWZhM2EzNzI1IiwibmFtZSI6ImFzYWQiLCJyb2xlIjoidXNlciJ9.qkxuTeWCxNEAgWP6Dr3xThlsyC3eyEB9mBk5hTGfDvE"

    override fun getSupplies(query: GetSuppliesQueryParams): Flow<Result<List<SupplyEntity>>> = flow {
        if (token.isNotBlank()) {
            val response = supplyApiDataSource.getSupplies(token, query)
            val resData = response.body()?.data?.data.orEmpty()

            if (response.isSuccessful && response.code() == 200) {
                emit(Result.Success(supplyMapper.mapSupplies(resData)))
            } else {
                emit(Result.Error("Response is not successful"))
            }
        } else {
            emit(Result.Error("Token is empty"))
        }
    }.catch {
        emit(Result.Error(it.message))
    }.flowOn(ioDispatcher)
}