package com.example.libs.data.source.network.model.response.supply

import com.google.gson.annotations.SerializedName

data class DeleteSuppliesResponse(
    @SerializedName("data")
    val `data`: Int? = null,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)
