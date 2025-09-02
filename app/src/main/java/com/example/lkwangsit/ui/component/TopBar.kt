@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lkwangsit.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lkwangsit.R
import com.example.lkwangsit.theme.LKWangsitTheme
import com.example.lkwangsit.ui.component.datalist.DataItem
import com.example.lkwangsit.ui.component.datalist.SingleActionMenu

@Composable
fun TopBar(
    title: String? = null,
    actions: List<SingleActionMenu>,
    selectedIds: Set<String>,
    onBackButtonClick: (() -> Unit)?,
    onFilterButtonClick: (() -> Unit)?,
    onSearchButtonClick: (() -> Unit)?,
    onDownloadButtonClick: (() -> Unit)?,
    onBulkSelectAllButtonClick: (() -> Unit)?,
) {
    TopAppBar(
        title = { Text(text = title ?: "Supplier List") },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick?.invoke() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        actions = {
            ListHeader(
                actions = actions,
                selectedIds = selectedIds,
                onFilterButtonClick = onFilterButtonClick,
                onSearchButtonClick = onSearchButtonClick,
                onDownloadButtonClick = onDownloadButtonClick,
                onBulkSelectAllButtonClick = onBulkSelectAllButtonClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun ListHeader(
    actions: List<SingleActionMenu>,
    selectedIds: Set<String>,
    onFilterButtonClick: (() -> Unit)?,
    onSearchButtonClick: (() -> Unit)?,
    onDownloadButtonClick: (() -> Unit)?,
    onBulkSelectAllButtonClick: (() -> Unit)?,
) {
    val showBulkMenu = remember { mutableStateOf(false) }
    val actionsSheetState = rememberModalBottomSheetState()

    if (selectedIds.isEmpty()) {
        if (onSearchButtonClick != null) {
            IconButton(onClick = { onSearchButtonClick() }) {
                Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search")
            }
        }

        if (onFilterButtonClick != null) {
            IconButton(onClick = { onFilterButtonClick() }) {
                Icon(painter = painterResource(id = R.drawable.ic_filter), contentDescription = "Filter")
            }
        }

        if (onDownloadButtonClick != null) {
            IconButton(onClick = { onDownloadButtonClick() }) {
                Icon(painter = painterResource(id = R.drawable.ic_download), contentDescription = "Download")
            }
        }
    } else {
        if (onBulkSelectAllButtonClick != null) {
            IconButton(onClick = { onBulkSelectAllButtonClick() }) {
                Icon(painter = painterResource(id = R.drawable.ic_select_all), contentDescription = "Bulk Select All")
            }
        }

        IconButton(onClick = { showBulkMenu.value = true }) {
            Icon(painter = painterResource(id = R.drawable.ic_more_2_line), contentDescription = "Bulk Actions")
        }
    }

    if (showBulkMenu.value) {
        ActionsSheet(
            selectedIds = selectedIds,
            sheetState = actionsSheetState,
            actions = actions,
            onDismiss = { showBulkMenu.value = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val sampleActions = listOf(
        SingleActionMenu(
            actionName = "Delete",
            actionIconRes = R.drawable.ic_delete_bin,
            action = {}
        ),
        SingleActionMenu(
            actionName = "Edit",
            actionIconVector = Icons.Default.Edit,
            action = {}
        )
    )

    val selectedIds = setOf("1", "2", "3")

    LKWangsitTheme(
        dynamicColor = false
    ) {
        TopBar(
            actions = sampleActions,
            selectedIds = selectedIds,
            onBackButtonClick = {},
            onFilterButtonClick = {},
            onSearchButtonClick = {},
            onDownloadButtonClick = {},
            onBulkSelectAllButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarBulkPreview() {
    val sampleActions = listOf(
        SingleActionMenu(
            actionName = "Delete",
            actionIconRes = R.drawable.ic_delete_bin,
            action = {}
        ),
        SingleActionMenu(
            actionName = "Edit",
            actionIconVector = Icons.Default.Edit,
            action = {}
        )
    )

    val selectedIds = setOf("1", "2", "3")

    LKWangsitTheme(
        dynamicColor = false
    ) {
        TopBar(
            actions = sampleActions,
            selectedIds = selectedIds,
            onBackButtonClick = {},
            onFilterButtonClick = {},
            onSearchButtonClick = {},
            onDownloadButtonClick = {},
            onBulkSelectAllButtonClick = {}
        )
    }
}