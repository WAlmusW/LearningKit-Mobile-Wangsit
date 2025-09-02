package com.example.lkwangsit.ui.component.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import com.example.lkwangsit.ui.component.enums.Severity

class SeveritySnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val severity: Severity = Severity.INFO
) : SnackbarVisuals

suspend fun SnackbarHostState.show(
    message: String,
    severity: Severity = Severity.INFO,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
    duration: SnackbarDuration = SnackbarDuration.Short
): SnackbarResult = showSnackbar(
    visuals = SeveritySnackbarVisuals(
        message = message,
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration,
        severity = severity
    )
)