package com.example.lkwangsit.ui.screen.supplies.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.lkwangsit.R
import com.example.lkwangsit.theme.LKWangsitTheme
import com.example.lkwangsit.ui.component.DialogConfirm
import com.example.lkwangsit.ui.component.enums.Severity
import com.example.lkwangsit.ui.component.datalist.DataList
import com.example.lkwangsit.ui.component.SegmentedTabMenu
import com.example.lkwangsit.ui.component.datalist.SingleActionMenu
import com.example.lkwangsit.ui.component.TopBar
import com.example.lkwangsit.ui.component.filter.FilterSheet
import com.example.lkwangsit.ui.component.snackbar.LkSnackbar
import com.example.lkwangsit.ui.component.snackbar.show
import com.example.lkwangsit.ui.screen.supplies.uistate.UiEvent
import com.example.lkwangsit.ui.screen.supplies.viewmodel.SuppliesViewModel

@Composable
fun SuppliesScreen() {
    val vm: SuppliesViewModel = hiltViewModel()
    val state by vm.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    var showFilterSheet by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var showActivateDialog by rememberSaveable { mutableStateOf(false) }
    var showInactivateDialog by rememberSaveable { mutableStateOf(false) }

    val deleteDialogMessage by remember(
        state.selectedIds, state.selectedId, state.supplies
    ) {
        derivedStateOf {
            when {
                state.selectedIds.isNotEmpty() -> {
                    val n = state.selectedIds.size
                    buildAnnotatedString {
                        append("You have selected ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$n data")
                        }
                        append(" to be deleted. Are you sure you want to continue?")
                    }
                }
                !state.selectedId.isNullOrBlank() -> {
                    val item = state.supplies.firstOrNull { it.id == state.selectedId }
                    val name = item?.title ?: "This item"
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(name)
                        }
                        append(" will be deleted. Are you sure you want to delete it?")
                    }
                }
                else -> {
                    AnnotatedString("Are you sure you want to delete?")
                }
            }
        }
    }

    val activateDialogMessage by remember(
        state.selectedIds, state.selectedId, state.supplies
    ) {
        derivedStateOf {
            when {
                state.selectedIds.isNotEmpty() -> {
                    val n = state.selectedIds.size
                    buildAnnotatedString {
                        append("You have selected ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$n supplier(s)")
                        }
                        append(" to be activated. Are you sure you want to continue?")
                    }
                }
                !state.selectedId.isNullOrBlank() -> {
                    val item = state.supplies.firstOrNull { it.id == state.selectedId }
                    val name = item?.title ?: "This item"
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(name)
                        }
                        append(" will be activated. Are you sure you want to activate it?")
                    }
                }
                else -> {
                    AnnotatedString("Are you sure you want to activate?")
                }
            }
        }
    }

    val inactivateDialogMessage by remember(
        state.selectedIds, state.selectedId, state.supplies
    ) {
        derivedStateOf {
            when {
                state.selectedIds.isNotEmpty() -> {
                    val n = state.selectedIds.size
                    buildAnnotatedString {
                        append("You have selected ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$n supplier(s)")
                        }
                        append(" to be inactivated. Are you sure you want to continue?")
                    }
                }
                !state.selectedId.isNullOrBlank() -> {
                    val item = state.supplies.firstOrNull { it.id == state.selectedId }
                    val name = item?.title ?: "This item"
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(name)
                        }
                        append(" will be inactivated. Are you sure you want to inactivate it?")
                    }
                }
                else -> {
                    AnnotatedString("Are you sure you want to inactivate?")
                }
            }
        }
    }

    val actions: List<SingleActionMenu> = listOf(
        SingleActionMenu(
            severity = Severity.SECONDARY,
            actionName = "Activate",
            actionIconVector = Icons.Default.Check,
            action = { showActivateDialog = true },
        ),
        SingleActionMenu(
            severity = Severity.SECONDARY,
            actionName = "Inactivate",
            actionIconVector = Icons.Default.Close,
            action = { showInactivateDialog = true },
        ),
        SingleActionMenu(
            severity = Severity.ERROR,
            actionName = "Delete",
            actionIconRes = R.drawable.ic_delete_bin,
            action = { showDeleteDialog = true },
        ),
    )

    LaunchedEffect(vm, snackbarHostState, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            vm.events.collect { event ->
                when (event) {
                    is UiEvent.Snackbar -> {
                        val res = snackbarHostState.show(
                            message = event.message,
                            severity = event.severity,
                            actionLabel = event.actionLabel,
                            withDismissAction = event.withDismissAction,
                            duration = event.duration
                        )
                        if (res == SnackbarResult.ActionPerformed) {
                            when (val r = event.retry) {
                                is UiEvent.Retry.Delete -> vm.onDeleteSupplies()
                                null -> Unit
                            }
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data -> LkSnackbar(data) } // your custom look
            )
        },
        topBar = {
            TopBar(
                title = if (state.selectedIds.isEmpty()) "Supplies List"
                        else state.selectedIds.size.toString(),
                actions = actions,
                selectedIds = state.selectedIds,
                onBackButtonClick = vm::backButtonClick,
                onFilterButtonClick = { showFilterSheet = true },
                onSearchButtonClick = { },
                onDownloadButtonClick = { },
                onBulkSelectAllButtonClick = vm::selectAllClick
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
                    items = state.supplies,
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

    if (showDeleteDialog) {
        DialogConfirm(
            iconRes = R.drawable.ic_delete_bin,
            iconVector = null,
            title = "Delete Supply",
            message = deleteDialogMessage,
            onDismissRequest = { showDeleteDialog = false },
            onCancel = { showDeleteDialog = false },
            onConfirm = {
                vm.onDeleteSupplies()
                showDeleteDialog = false
            },
            severity = Severity.ERROR,
        )
    }

    if (showActivateDialog) {
        DialogConfirm(
            iconRes = null,
            iconVector = Icons.Default.Check,
            title = "Activate Supply",
            message = activateDialogMessage,
            onDismissRequest = { showActivateDialog = false },
            onCancel = { showActivateDialog = false },
            onConfirm = {
                vm.onActivateSupplies()
                showActivateDialog = false
            },
            severity = Severity.SUCCESS
        )
    }

    if (showInactivateDialog) {
        DialogConfirm(
            iconRes = null,
            iconVector = Icons.Default.Close,
            title = "Inactivate Supply",
            message = inactivateDialogMessage,
            onDismissRequest = { showInactivateDialog = false },
            onCancel = { showInactivateDialog = false },
            onConfirm = {
                vm.inactivateSupplies()
                showInactivateDialog = false
            },
            severity = Severity.ERROR
        )
    }
}