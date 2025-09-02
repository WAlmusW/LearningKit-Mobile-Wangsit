package com.example.lkwangsit.model.supplies

data class SuppliesFilterOption(
    val activeOption: List<OptionData<String>> = emptyList(),
    val suppliesOption: List<OptionData<String>> = emptyList(),
    val itemOption: List<OptionData<String>> = emptyList(),
    val modifiedByOption: List<OptionData<String>> = emptyList(),
)
