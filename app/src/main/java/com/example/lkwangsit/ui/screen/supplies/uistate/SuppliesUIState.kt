package com.example.lkwangsit.ui.screen.supplies.uistate

import androidx.compose.material3.SnackbarDuration
import com.example.lkwangsit.ui.component.datalist.DataItem
import com.example.lkwangsit.ui.component.enums.Severity
import com.example.lkwangsit.ui.component.filter.FilterSpec
import com.example.lkwangsit.ui.component.filter.FilterValue

data class SuppliesUIState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val tabs: List<String> = emptyList(),
    val selectedTabIndex: Int = 0,

    val filterSpecs: List<FilterSpec> = emptyList(),
    val appliedFilters: Map<String, FilterValue> = emptyMap(),

    val supplies: List<DataItem> = emptyList(),
    val selectionMode: Boolean = false,
    val selectedId: String? = null,
    val selectedIds: Set<String> = emptySet(),
)

sealed class UiEvent {
    data class Snackbar(
        val message: String,
        val severity: Severity,
        val actionLabel: String? = null,
        val withDismissAction: Boolean = true,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val retry: Retry? = null
    ) : UiEvent()

    sealed class Retry {
        data class Delete(val id: String) : Retry()
    }
}