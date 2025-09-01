package com.example.libs.data.model

data class SupplyEntity(
    val id: String = "",
    val companyName: String = "",
    val status: String = "",
    val country: String = "",
    val state: String = "",
    val item: List<Item> = listOf(),
    val picName: String = "",
    val updatedAt: String = "",
)

data class Item(
    val itemName: String,
    val sku: List<String>
)