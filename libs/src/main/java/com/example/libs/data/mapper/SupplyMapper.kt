package com.example.libs.data.mapper

import com.example.libs.data.model.Item
import com.example.libs.data.model.SupplyEntity
import com.example.libs.data.source.network.model.response.supply.GetSuppliesResponse
import javax.inject.Inject

class SupplyMapper @Inject constructor() {
    fun mapSupplies(data: List<GetSuppliesResponse.Data.Data>): List<SupplyEntity> {
        return data.map {
            SupplyEntity(
                id = it.id,
                companyName = it.companyName,
                status = it.status,
                country = it.country,
                state = it.state,
                item = it.item.map { item ->
                    Item(
                        itemName = item.itemName,
                        sku = item.sku
                    )
                },
                picName = it.picName,
                updatedAt = it.updatedAt

            )
        }
    }
}