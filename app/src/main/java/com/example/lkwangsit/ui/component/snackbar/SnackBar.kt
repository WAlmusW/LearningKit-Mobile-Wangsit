package com.example.lkwangsit.ui.component.snackbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lkwangsit.R
import com.example.lkwangsit.theme.LKWangsitTheme
import com.example.lkwangsit.ui.component.enums.Severity

@Composable
fun SnackBar(
    message: String,
    severity: Severity,
    modifier: Modifier = Modifier,
    iconRes: Int? = null,
    iconVector: ImageVector? = null,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
    onAction: (() -> Unit)? = null,
    onDismiss: () -> Unit,
) {
    val container = severity.mainColor()
    val content = severity.textColor()

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(percent = 50),
        color = container,
        contentColor = content,
        tonalElevation = 6.dp,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                iconRes != null -> Icon(painterResource(iconRes), contentDescription = null)
                iconVector != null -> Icon(iconVector, contentDescription = null)
            }
            Text(
                text = message,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f, fill = true)
            )
            actionLabel?.let { label ->
                TextButton(onClick = { onAction?.invoke() }) {
                    Text(label)
                }
            }
            if (withDismissAction) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Dismiss")
                }
            }
        }
    }
}



@Preview
@Composable
fun SnackBarPreview() {
    LKWangsitTheme(
        dynamicColor = false
    ) {
        Column {
            SnackBar(
                iconRes = null,
                iconVector = null,
                message = "This is a snackbar",
                severity = Severity.SUCCESS,
                onDismiss = {},
            )
            SnackBar(
                iconRes = null,
                iconVector = null,
                message = "This is a snackbar",
                severity = Severity.WARNING,
                onDismiss = {},
            )
            SnackBar(
                iconRes = R.drawable.ic_delete_bin,
                iconVector = null,
                message = "This is a snackbar",
                severity = Severity.ERROR,
                onDismiss = {},
            )
        }
    }
}