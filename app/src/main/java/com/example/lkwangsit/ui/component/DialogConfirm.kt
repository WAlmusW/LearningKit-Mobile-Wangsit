package com.example.lkwangsit.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lkwangsit.R
import com.example.lkwangsit.theme.LKWangsitTheme
import com.example.lkwangsit.theme.LocalExtraColors
import com.example.lkwangsit.ui.component.enums.Severity

@Composable
fun DialogConfirm(
    modifier: Modifier = Modifier,
    iconRes: Int? = null,
    iconVector: ImageVector? = null,
    title: String,
    message: String,
    cancelButtonLabel: String = "Cancel",
    confirmButtonLabel: String = "Confirm",
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    severity: Severity,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = modifier
                    .padding(vertical = 22.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    if (iconRes != null) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null
                        )
                    } else if (iconVector != null) {
                        Icon(
                            imageVector = iconVector,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                ) {
                    OutlinedButton(
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(2.dp, LocalExtraColors.current.neutral),
                        onClick = onCancel
                    ) {
                        Text(
                            text = cancelButtonLabel,
                            color = LocalExtraColors.current.onNeutral
                        )
                    }
                    Button(
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = severity.mainColor()
                        ),
                        onClick = onConfirm
                    ) {
                        Text(
                            text = confirmButtonLabel,
                            color = severity.textColor()
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun DialogConfirmErrorPreview() {
    LKWangsitTheme(
        dynamicColor = false
    ) {
        DialogConfirm(
            iconRes = R.drawable.ic_delete_bin,
            iconVector = null,
            title = "Delete Supply",
            message = "PT. ABC Indonesia will be deleted. Are you sure you want to delete it?",
            onDismissRequest = { },
            onCancel = { },
            onConfirm = { },
            severity = Severity.ERROR
        )
    }
}

@Preview
@Composable
fun DialogConfirmSuccessPreview() {
    LKWangsitTheme(
        dynamicColor = false
    ) {
        DialogConfirm(
            iconRes = null,
            iconVector = Icons.Default.Done,
            title = "Delete Supply",
            message = "PT. ABC Indonesia will be deleted. Are you sure you want to delete it?",
            onDismissRequest = { },
            onCancel = { },
            onConfirm = { },
            severity = Severity.SUCCESS
        )
    }
}