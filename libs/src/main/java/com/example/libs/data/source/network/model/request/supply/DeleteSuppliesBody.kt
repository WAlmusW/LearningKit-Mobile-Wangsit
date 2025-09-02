package com.example.libs.data.source.network.model.request.supply


import com.google.gson.annotations.SerializedName

data class DeleteSuppliesBody(
    @SerializedName("supplierID")
    val supplierID: List<String> = emptyList()
)