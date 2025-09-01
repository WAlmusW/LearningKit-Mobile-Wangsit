@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lkwangsit.ui.component.datalist

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lkwangsit.theme.LKWangsitTheme
import com.example.lkwangsit.ui.Severity
import com.example.lkwangsit.ui.component.ActionsSheet
import com.example.lkwangsit.ui.component.Chip
import com.example.lkwangsit.ui.component.ChipGroup
import com.example.lkwangsit.ui.component.ChipItem
import com.example.lkwangsit.ui.component.filter.toggled
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DataList(
    modifier: Modifier,
    items: List<DataItem>,
    selectedId: String? = null,                 // For single-selection
    selectedIds: Set<String> = emptySet(),      // For bulk-selection

    useSingleActionMenu: Boolean = true,
    actions: List<SingleActionMenu>,
    selectionMode: Boolean = false,
    onActionClick: (DataItem) -> Unit = {},
    onItemClick: (DataItem) -> Unit = {},
    onItemLongClick: (DataItem) -> Unit = {},
) {
    LazyColumn {
        itemsIndexed(items) { idx, item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                DataRow(
                    item,
                    useSingleActionMenu,
                    actions,
                    selectionMode,
                    selectedId,
                    selectedIds,
                    onActionClick,
                    onItemClick,
                    onItemLongClick,
                )
            }
        }
    }
}

@Composable
fun DataRow(
    item: DataItem,
    useSingleActionMenu: Boolean,
    actions: List<SingleActionMenu>?,
    selectionMode: Boolean,
    selectedId: String? = null,
    selectedIds: Set<String>,
    onActionClick: (DataItem) -> Unit = {},
    onItemClick: (DataItem) -> Unit = {},
    onItemLongClick: (DataItem) -> Unit = {},
) {
    val showMenu = remember { mutableStateOf(false) }
    val actionsSheetState = rememberModalBottomSheetState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {onItemClick.invoke(item)},
                onLongClick = {onItemLongClick.invoke(item)}
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selectionMode && selectedIds.contains(item.id)) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        ) {
            // Data Info
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                // Header
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item.status?.let { Chip(it) }
                    item.title?.let { Text(it, style = MaterialTheme.typography.titleSmall) }
                    item.subtitle?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
                }

                // Content
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item.description?.let { Text(it, style = MaterialTheme.typography.labelSmall) }
                    item.listItem?.let { ChipGroup(it, item.listItemLimitShown) }
                }

                // Footer
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val date = item.updatedAt.let {
                        val fmt = SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.getDefault())
                        fmt.format(it)
                    }
                    Text(date, style = MaterialTheme.typography.bodyMedium)
                    item.updatedBy?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
                }
            }

            // Single Action Button
            if (useSingleActionMenu) {
                IconButton(
                    onClick = {
                        onActionClick(item)
                        showMenu.value = true
                    }
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Single Action Button")
                }
            }
        }
    }

    if (actions != null && showMenu.value) {
        ActionsSheet(
            selectedIds = selectedIds,
            sheetState = actionsSheetState,
            actions = actions,
            onDismiss = { showMenu.value = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DataListPreview() {
    val sampleItems = listOf(
        DataItem(
            id = "1",
            status = ChipItem("Active", Severity.SUCCESS),
            title = "Supplier A",
            subtitle = "Category: Raw Materials",
            description = "This is a description of Supplier A with some details.",
            listItem = listOf(
                ChipItem("PO Required", Severity.INFO),
                ChipItem("Priority", Severity.WARNING),
                ChipItem("Verified", Severity.SUCCESS)
            ),
            listItemLimitShown = 2,
            updatedAt = System.currentTimeMillis(),
            updatedBy = "Admin"
        ),
        DataItem(
            id = "2",
            status = ChipItem("Inactive", Severity.ERROR),
            title = "Supplier B",
            subtitle = "Category: Finished Goods",
            description = "Description of Supplier B.",
            listItem = listOf(
                ChipItem("Documents Missing", Severity.ERROR),
                ChipItem("Follow Up", Severity.SECONDARY)
            ),
            updatedAt = System.currentTimeMillis() - 86_400_000L, // yesterday
            updatedBy = "System"
        )
    )

    val sampleActions = listOf(
        SingleActionMenu(
            actionName = "Edit",
            actionIconVector = Icons.Default.MoreVert,
            action = { /* No-op in preview */ }
        ),
        SingleActionMenu(
            actionName = "Delete",
            actionIconVector = Icons.Default.MoreVert,
            action = { /* No-op in preview */ }
        )
    )

    LKWangsitTheme {
        var selectedId by remember { mutableStateOf<String?>(null) }
        var selectedIds by remember { mutableStateOf(setOf<String>()) }
        var selectionMode by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            DataList(
                modifier = Modifier,
                items = sampleItems,
                useSingleActionMenu = true,
                actions = sampleActions,
                selectedId = null,
                selectedIds = selectedIds,
                onActionClick = {
                    selectedId = it.id
                },
                onItemClick = {
                    if (selectionMode && selectedIds.contains(it.id) && selectedIds.size == 1) {
                        selectionMode = false
                    } else if (selectionMode) {
                        selectedIds.toggled(it.id)
                    }
                },
                onItemLongClick = {
                    if (!selectionMode) {
                        selectionMode = true
                        selectedIds = setOf(it.id)
                    } else {
                        selectedIds.toggled(it.id)
                    }
                },
                selectionMode = selectionMode,
            )
        }
    }
}
