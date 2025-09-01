package com.example.lkwangsit.ui.screen.supplierlist.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lkwangsit.R
import com.example.lkwangsit.theme.LKWangsitTheme
import com.example.lkwangsit.ui.Severity
import com.example.lkwangsit.ui.component.datalist.DataList
import com.example.lkwangsit.ui.component.SegmentedTabMenu
import com.example.lkwangsit.ui.component.datalist.SingleActionMenu
import com.example.lkwangsit.ui.component.TopBar
import com.example.lkwangsit.ui.component.filter.FilterSheet
import com.example.lkwangsit.ui.screen.supplierlist.viewmodel.SupplierListViewModel

@Composable
fun SupplierListScreen() {
    val vm: SupplierListViewModel = hiltViewModel()
    val state by vm.state.collectAsStateWithLifecycle()

    var showFilterSheet by rememberSaveable { mutableStateOf(false) }

    val actions: List<SingleActionMenu> = listOf(
        SingleActionMenu(
            actionName = "Edit",
            actionIconVector = Icons.Default.Edit,
            severity = Severity.INFO
        ) { /* no-op */ },
        SingleActionMenu(
            actionName = "Delete",
            actionIconRes = R.drawable.ic_delete_bin,
            severity = Severity.ERROR
        ) { /* no-op */ },
        SingleActionMenu(
            actionName = "Mark as Done",
            actionIconVector = Icons.Default.Check,
            severity = Severity.SUCCESS
        ) { /* no-op */ }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = if (state.selectedIds.isEmpty()) "Supplier List"
                        else state.selectedIds.size.toString(),
                actions = actions,
                selectedIds = state.selectedIds,
                onFilterButtonClick = { showFilterSheet = true },
                onSearchButtonClick = { },
                onDownloadButtonClick = { },
                onBulkSelectAllButtonClick = { }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Surface(color = MaterialTheme.colorScheme.primary) {
                SegmentedTabMenu(
                    selectedTabIndex = state.selectedTabIndex,
                    onSelectedChange = vm::onTabSelected,
                    tabs = state.tabs,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                DataList(
                    modifier = Modifier,
                    items = state.supplierList,
                    useSingleActionMenu = true,
                    actions = actions,
                    selectedId = state.selectedId,
                    selectedIds = state.selectedIds,
                    onActionClick = vm::onActionClick,
                    onItemClick = vm::onItemClick,
                    onItemLongClick = vm::onItemLongClick,
                    selectionMode = state.selectionMode,
                )
            }
        }
    }

    if (showFilterSheet) {
        FilterSheet(
            specs = state.filterSpecs,
            initialValues = state.appliedFilters,
            onDismissRequest = { showFilterSheet = false },
            onApply = {
                vm.applyFilters(it)
                showFilterSheet = false
            },
            onReset = vm::resetFilters
        )
    }
}


@Preview
@Composable
fun SupplierListScreenPreview() {
    LKWangsitTheme(dynamicColor = false) {
        SupplierListScreen()
    }
}