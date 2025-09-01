package com.example.lkwangsit.ui.screen.supplierlist.uistate

import com.example.lkwangsit.ui.component.datalist.DataItem
import com.example.lkwangsit.ui.component.filter.FilterSpec
import com.example.lkwangsit.ui.component.filter.FilterValue

data class  SupplierListUIState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val tabs: List<String> = emptyList(),
    val selectedTabIndex: Int = 0,

    val filterSpecs: List<FilterSpec> = emptyList(),
    val appliedFilters: Map<String, FilterValue> = emptyMap(),

    val supplierList: List<DataItem> = emptyList(),
    val selectionMode: Boolean = false,
    val selectedId: String? = null,
    val selectedIds: Set<String> = emptySet(),
)