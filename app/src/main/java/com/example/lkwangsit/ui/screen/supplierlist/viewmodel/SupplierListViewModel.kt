package com.example.lkwangsit.ui.screen.supplierlist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libs.base.Result
import com.example.libs.data.source.network.model.request.supply.GetSuppliesQueryParams
import com.example.libs.domain.supply.GetSuppliesUseCase
import com.example.lkwangsit.ui.Severity
import com.example.lkwangsit.ui.component.ChipItem
import com.example.lkwangsit.ui.component.datalist.DataItem
import com.example.lkwangsit.ui.component.filter.*
import com.example.lkwangsit.ui.screen.supplierlist.uistate.SupplierListUIState
import com.example.lkwangsit.util.DateUtil.toEpochMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SupplierListViewModel @Inject constructor(
    private val getSuppliesUseCase: GetSuppliesUseCase
) : ViewModel() {

    //region State
    private val _state = MutableStateFlow(SupplierListUIState())
    val state = _state.asStateFlow()
    //endregion

    init {
        loadTabMenu()
        loadFilterSpecs()
        refresh()
    }

    //region Intents (public)
    fun applyFilters(values: Map<String, FilterValue>) {
        _state.update { it.copy(appliedFilters = values) }
        refresh()
    }

    fun resetFilters() {
        _state.update { it.copy(appliedFilters = emptyMap()) }
        refresh()
    }

    fun onTabSelected(index: Int) {
        _state.update { it.copy(selectedTabIndex = index) }
        fetchSupplies()
    }

    fun onActionClick(item: DataItem) {
        _state.update { it.copy(selectedId = item.id) }
        Log.i("item", "action")
    }

    fun onItemClick(item: DataItem) {
        if (state.value.selectionMode && state.value.selectedIds.contains(item.id)) {
            _state.update { it.copy(selectedIds = it.selectedIds - item.id) }
        } else if (state.value.selectionMode) {
            _state.update { it.copy(selectedIds = it.selectedIds + item.id) }
        }

        if (state.value.selectionMode && (state.value.selectedIds.size == 1) && (state.value.selectedIds.contains(item.id))) {
            _state.update { it.copy(selectionMode = false) }
        }
    }

    fun onItemLongClick(item: DataItem) {
        if (!state.value.selectedIds.contains(item.id)) {
            _state.update { it.copy(selectedIds = it.selectedIds + item.id) }
        } else {
            _state.update { it.copy(selectedIds = it.selectedIds - item.id) }
        }

        if (!state.value.selectionMode) {
            _state.update { it.copy(selectionMode = !it.selectionMode) }
            Log.i("item", state.value.selectionMode.toString())
        }
    }

    fun refresh() {
        fetchSupplies()
    }
    //endregion

    //region tabmenu
    private fun loadTabMenu() {
        _state.update { it.copy(tabs = listOf("Supplier", "Supplier Activities")) }
    }

    private fun fetchSupplies() {
        _state.update { it.copy(isLoading = true, error = null) }

        getSuppliesUseCase(GetSuppliesQueryParams()).onEach { result ->
            when (result) {
                is Result.Success -> {
                    val dataItem = result.data.map { item ->
                        DataItem(
                            id = item.id,
                            title = item.companyName,
                            subtitle = item.state + ", " + item.country,
                            listItemLimitShown = 2,
                            listItem = item.item.map {
                                ChipItem(
                                    it.itemName,
                                    Severity.INFO
                                )
                            },
                            status = ChipItem(
                                text = item.status,
                                severity = when (item.status) {
                                    "Active" -> Severity.SUCCESS
                                    "Inactive" -> Severity.ERROR
                                    else -> Severity.INFO
                                }
                            ),
                            updatedAt = item.updatedAt.toEpochMillis(),
                            updatedBy = item.picName,
                        )
                    }

                    _state.update { it.copy(isLoading = false, supplierList = dataItem) }
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }.launchIn(viewModelScope)
    }

    //region Filter specs
    object FilterIds {
        const val ACTIVE = "active"
        const val SUPPLIER = "supplier"
        const val ITEM = "item"
        const val MODIFIED_BY = "modifiedBy"
        const val LAST_MODIFIED = "lastModified"
    }

    private fun loadFilterSpecs() {
        val specs = buildFilterSpecs()
        _state.update { it.copy(filterSpecs = specs) }
    }

    private fun buildFilterSpecs(): List<FilterSpec> {
        // In real app, load these from repo (e.g., options API), then map to Option(...)
        val supplierOpts = listOf(
            Option("sup-abc", "PT. ABC Indonesia"),
            Option("sup-bcd", "PT. BCD Indonesia"),
            Option("sup-opq", "PT. OPQ Indonesia"),
            Option("sup-bcd2", "PT. BCD Indonesia")
        )
        val itemOpts = listOf(
            Option("kecap", "Kecap"),
            Option("airmin", "Air Mineral"),
            Option("kertas", "Kertas HVS"),
            Option("saos", "Saos"),
            Option("laptop", "Laptop")
        )
        val userOpts = listOf(
            Option("pratama", "Pratama"),
            Option("budi", "Budi"),
            Option("john", "John Doe"),
            Option("jane", "Jane Doe"),
            Option("mark", "Mark Lee")
        )

        return listOf(
            OptionsFilterSpec(
                id = FilterIds.ACTIVE,
                title = "Active",
                options = listOf(Option("active", "Active"), Option("inactive", "Inactive")),
                limitShown = 2,
                showSeeAll = false
            ),
            OptionsFilterSpec(
                id = FilterIds.SUPPLIER,
                title = "Supplier",
                options = supplierOpts,
                limitShown = 3,
                showSeeAll = true
            ),
            OptionsFilterSpec(
                id = FilterIds.ITEM,
                title = "Item Name",
                options = itemOpts,
                limitShown = 4,
                showSeeAll = true
            ),
            OptionsFilterSpec(
                id = FilterIds.MODIFIED_BY,
                title = "Modified by",
                options = userOpts,
                limitShown = 4,
                showSeeAll = true
            ),
            DatePickerFilterSpec(
                id = FilterIds.LAST_MODIFIED,
                title = "Last Modified"
            )
        )
    }
    //endregion
}