package com.example.lkwangsit.ui.component.enums

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.lkwangsit.theme.LocalExtraColors

enum class Severity(
    val mainColor: @Composable () -> Color,
    val textColor: @Composable () -> Color
) {
    INFO(
        mainColor = { MaterialTheme.colorScheme.primary },
        textColor = { MaterialTheme.colorScheme.onPrimary }
    ),
    WARNING(
        mainColor = { MaterialTheme.colorScheme.primary },
        textColor = { MaterialTheme.colorScheme.onPrimary }
    ),
    ERROR(
        mainColor = { LocalExtraColors.current.onError },
        textColor = { LocalExtraColors.current.error }
    ),
    SUCCESS(
        mainColor = { LocalExtraColors.current.onSuccess },
        textColor = { LocalExtraColors.current.success }
    ),
    SECONDARY(
        mainColor = { LocalExtraColors.current.onSecondary },
        textColor = { LocalExtraColors.current.secondary }
    )
}