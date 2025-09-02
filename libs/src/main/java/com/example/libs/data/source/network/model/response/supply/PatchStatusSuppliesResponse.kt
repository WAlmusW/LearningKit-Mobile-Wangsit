package com.example.libs.data.source.network.model.response.supply

import com.google.gson.annotations.SerializedName

data class PatchStatusSuppliesResponse(
    @SerializedName("data")
    val `data`: Any? = null,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)
