package com.example.lkwangsit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lkwangsit.theme.LocalExtraColors
import com.example.lkwangsit.ui.Severity

data class ChipItem (
    val text: String,
    val severity: Severity
)

@Composable
fun Chip(chipItem: ChipItem) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        color = when (chipItem.severity) {
            Severity.SUCCESS -> LocalExtraColors.current.success
            Severity.ERROR -> LocalExtraColors.current.error
            Severity.SECONDARY -> LocalExtraColors.current.secondary
            else -> MaterialTheme.colorScheme.surface
        },
        modifier = Modifier.padding(end = 6.dp),
    ) {
        Text(
            text = chipItem.text,
            style = MaterialTheme.typography.bodySmall,
            color = when (chipItem.severity) {
                Severity.SUCCESS -> LocalExtraColors.current.onSuccess
                Severity.ERROR -> LocalExtraColors.current.onError
                else -> LocalExtraColors.current.onSecondary
            },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ChipGroup (
    chipItems: List<ChipItem>,
    limitShown: Int = 2
) {
    val chipsToBeShown = if (chipItems.size > limitShown) {
        chipItems.take(limitShown)
    } else {
        chipItems
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        chipsToBeShown.forEach {
            Chip(chipItem = it)
        }

        if (chipItems.size > limitShown) {
            val remainingChips = chipItems.size - limitShown
            Text(
                text = "+$remainingChips More",
                style = MaterialTheme.typography.bodySmall,
                color = LocalExtraColors.current.onSecondary
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChipPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Chip(ChipItem("Info", Severity.INFO))
        Chip(ChipItem("Warning", Severity.WARNING))
        Chip(ChipItem("Error", Severity.ERROR))
        Chip(ChipItem("Success", Severity.SUCCESS))
        Chip(ChipItem("Secondary", Severity.SECONDARY))
    }
}

@Preview(showBackground = true)
@Composable
fun ChipGroupPreview() {
    val chipItems = listOf(
        ChipItem("Kotlin", Severity.SUCCESS),
        ChipItem("Compose", Severity.INFO),
        ChipItem("Hilt", Severity.WARNING),
        ChipItem("Material3", Severity.ERROR),
        ChipItem("Clean Architecture", Severity.SECONDARY),
    )

    ChipGroup(chipItems = chipItems, limitShown = 3)
}



