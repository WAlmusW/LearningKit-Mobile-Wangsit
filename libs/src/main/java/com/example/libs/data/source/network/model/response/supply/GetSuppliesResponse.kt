package com.example.libs.data.source.network.model.response.supply


import com.google.gson.annotations.SerializedName

data class GetSuppliesResponse(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0,
) {
    data class Data(
        @SerializedName("totalRecords")
        val totalRecords: Int = 0,
        @SerializedName("data")
        val `data`: List<Data> = listOf()
    ) {
        data class Data(
            @SerializedName("_id")
            val id: String = "",
            @SerializedName("status")
            val status: String = "",
            @SerializedName("companyName")
            val companyName: String = "",
            @SerializedName("country")
            val country: String = "",
            @SerializedName("state")
            val state: String = "",
            @SerializedName("item")
            val item: List<Item> = listOf(),
            @SerializedName("picName")
            val picName: String = "",
            @SerializedName("updatedAt")
            val updatedAt: String = ""
        ) {
            data class Item(
                @SerializedName("itemName")
                val itemName: String = "",
                @SerializedName("sku")
                val sku: List<String> = listOf()
            )
        }
    }
}