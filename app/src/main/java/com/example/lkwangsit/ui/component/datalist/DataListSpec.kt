package com.example.lkwangsit.ui.component.datalist

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.lkwangsit.ui.component.enums.Severity
import com.example.lkwangsit.ui.component.ChipItem


data class DataItem(
    val id: String,
    val status: ChipItem? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val listItem: List<ChipItem>? = null,
    val listItemLimitShown: Int = 2,
    val updatedAt: Long? = null,
    val updatedBy: String? = null,
)

data class SingleActionMenu(
    val actionName: String,
    val actionIconRes: Int? = null,
    val actionIconVector: ImageVector? = null,
    val severity: Severity = Severity.SECONDARY,
    val action: () -> Unit
)