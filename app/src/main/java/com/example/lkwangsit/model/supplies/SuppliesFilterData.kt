package com.example.lkwangsit.model.supplies

data class SuppliesFilterData(
    val activeSelected: List<String> = emptyList(),
    val suppliesSelected: List<String> = emptyList(),
    val itemSelected: List<String> = emptyList(),
    val modifiedBySelected: List<String> = emptyList(),
    val lastModifiedSelected: Long? = null,
)
