package com.example.lkwangsit.ui.component.snackbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import com.example.lkwangsit.ui.component.enums.Severity

@Composable
fun LkSnackbar(data: SnackbarData) {
    val visuals = data.visuals
    val severity = (visuals as? SeveritySnackbarVisuals)?.severity ?: Severity.INFO

    val icon = when (severity) {
        Severity.INFO      -> Icons.Default.Info
        Severity.SUCCESS   -> Icons.Default.CheckCircle
        Severity.WARNING   -> Icons.Default.Warning
        Severity.ERROR     -> Icons.Default.Info
        Severity.SECONDARY -> Icons.Default.Info
    }

    SnackBar(
        message = visuals.message,
        severity = severity,
        iconVector = icon,
        actionLabel = visuals.actionLabel,
        withDismissAction = visuals.withDismissAction,
        onAction = { data.performAction() },
        onDismiss = { data.dismiss() },
    )
}