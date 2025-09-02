package com.example.libs.data.source.network.model.request.supply


import com.google.gson.annotations.SerializedName

data class PatchStatusSuppliesBody(
    @SerializedName("supplierID")
    val supplierID: List<String> = listOf(),
    @SerializedName("status")
    val status: String = ""
)