@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lkwangsit.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lkwangsit.R
import com.example.lkwangsit.theme.LKWangsitTheme
import com.example.lkwangsit.theme.LocalExtraColors
import com.example.lkwangsit.ui.Severity
import com.example.lkwangsit.ui.component.datalist.SingleActionMenu

@Composable
fun ActionsSheet(
    selectedIds: Set<String>,
    sheetState: SheetState,
    actions: List<SingleActionMenu>,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        sheetMaxWidth = Dp.Unspecified,
    ) {
        actions.forEach { action ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        action.action()
                        onDismiss()
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                if (action.actionIconRes != null) {
                    Icon(
                        painter = painterResource(id = action.actionIconRes),
                        contentDescription = action.actionName
                    )
                } else if (action.actionIconVector != null) {
                    Icon(
                        imageVector = action.actionIconVector,
                        contentDescription = action.actionName,
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    action.actionName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = when (action.severity) {
                        Severity.SUCCESS -> LocalExtraColors.current.onSuccess
                        Severity.ERROR -> LocalExtraColors.current.onError
                        else -> LocalExtraColors.current.onSecondary
                    }
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ActionsSheetPreview() {
    LKWangsitTheme {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        val selectedIds = setOf("1", "2", "3")

        val sampleActions = listOf(
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

        ActionsSheet(
            selectedIds = selectedIds,
            sheetState = sheetState,
            actions = sampleActions,
            onDismiss = { /* no-op for preview */ }
        )
    }
}