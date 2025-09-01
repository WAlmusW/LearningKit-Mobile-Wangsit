@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lkwangsit.ui.component.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lkwangsit.theme.LKWangsitTheme
import com.example.lkwangsit.util.DateUtil.toFormattedDate
import com.example.lkwangsit.util.isValidNumber


@Composable
fun FilterSheet(
    modifier: Modifier = Modifier,
    specs: List<FilterSpec>,
    initialValues: Map<String, FilterValue> = emptyMap(),
    onApply: (Map<String, FilterValue>) -> Unit,
    onDismissRequest: () -> Unit,
    onReset: (() -> Unit)? = null,
) {
    val currentValues = remember {
        mutableStateMapOf<String, FilterValue>().apply { putAll(initialValues) }
    }

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Filter", style = MaterialTheme.typography.titleMedium)
                    TextButton(
                        enabled = !currentValues.values.isEmpty(),
                        onClick = { onReset?.invoke() }
                    ) { Text("Reset") }
                }

                Spacer(modifier.height(8.dp))
            }

            items(specs.size) { index ->
                when (val spec = specs[index]) {
                    is OptionsFilterSpec      -> OptionsSection(spec, currentValues)
                    is NumberRangeFilterSpec  -> NumberRangeSection(spec, currentValues)
                    is DateRangeFilterSpec    -> { }
                    is DatePickerFilterSpec   -> DatePickerSection(spec, currentValues)
                }
                Spacer(modifier.height(12.dp))
            }

            item {
                Spacer(modifier.height(8.dp))
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    onClick = { onApply(currentValues) }
                ) { Text("Apply") }
            }
        }
    }
}

@Composable
private fun OptionsSection(
    spec: OptionsFilterSpec,
    values: SnapshotStateMap<String, FilterValue>,
) {
    var showAll by remember { mutableStateOf(false) }
    val current = values[spec.id] as? OptionFilterValue ?: OptionFilterValue(spec.id)

    SectionHeader(
        title = spec.title,
        trailing = if (spec.showSeeAll && spec.options.size > spec.limitShown) {
            { TextButton(onClick = { showAll = !showAll }) { Text(if (showAll) "Show less" else "See all") } }
        } else null
    )

    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val items = if (showAll) spec.options else spec.options.take(spec.limitShown)
        items.forEach { opt ->
            FilterChip(
                selected = opt.id in current.selectedOptions,
                onClick = {
                    val next = current.copy(selectedOptions = current.selectedOptions.toggled(opt.id))

                    if (next.selectedOptions.isEmpty()) values.remove(spec.id)
                    else values[spec.id] = next
                },
                label = { Text(opt.label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                shape = RoundedCornerShape(50),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    labelColor = MaterialTheme.colorScheme.primary,
                    iconColor = MaterialTheme.colorScheme.primary,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                ),
                leadingIcon = {
                    if (opt.id in current.selectedOptions) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Selected"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Select"
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun NumberRangeSection(
    spec: NumberRangeFilterSpec,
    values: SnapshotStateMap<String, FilterValue>,
) {
    val current = values[spec.id] as? NumberRangeFilterValue ?: NumberRangeFilterValue(spec.id)

    var fromText by remember(current.from) { mutableStateOf(current.from?.toString().orEmpty()) }
    var toText   by remember(current.to)   { mutableStateOf(current.to?.toString().orEmpty()) }

    fun commit() {
        val from = fromText.toDoubleOrNull()
        val to   = toText.toDoubleOrNull()

        val boundedFrom = spec.min?.let { if (from != null) maxOf(it, from) else null } ?: from
        val boundedTo   = spec.max?.let { if (to   != null) minOf(it, to  ) else null } ?: to

        // Reverse, if user accidentally input from > to
        val (finalFrom, finalTo) =
            if (boundedFrom != null && boundedTo != null && boundedFrom > boundedTo)
                boundedTo to boundedFrom
            else boundedFrom to boundedTo

        val next = current.copy(specId = spec.id, from = finalFrom, to = finalTo)

        if (next.from == null && next.to == null) values.remove(spec.id)
        else values[spec.id] = next
    }

    SectionHeader(title = spec.title)

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = fromText,
            onValueChange = { input ->
                if (input.isValidNumber()) {
                    fromText = input
                    commit()
                }
            },
            label = { Text("Min") },
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = toText,
            onValueChange = { input ->
                if (input.isValidNumber()) {
                    toText = input
                    commit()
                }
            },
            label = { Text("Max") },
            singleLine = true
        )
    }
}

@Composable
private fun DatePickerSection(
    spec: DatePickerFilterSpec,
    values: SnapshotStateMap<String, FilterValue>,
) {
    val current = values[spec.id] as? DatePickerFilterValue ?: DatePickerFilterValue(spec.id)

    val display = current.dateMillis?.toFormattedDate() ?: ""
    var showModal by remember { mutableStateOf(false) }

    SectionHeader(title = spec.title)

    OutlinedTextField(
        value = display,
        onValueChange = { input ->
            if (input.isValidNumber()) {
                val millis = input.toLongOrNull()
                if (millis != null) {
                    val next = current.copy(specId = spec.id, dateMillis = millis)

                    if (input.isEmpty()) values.remove(spec.id)
                    else values[spec.id] = next
                }
            }
        },
        placeholder = { Text("Select date") },
        readOnly = true,
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { showModal = true })
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { input ->
                if (input != null) {
                    val next = current.copy(specId = spec.id, dateMillis = input)
                    values[spec.id] = next
                }
            },
            onDismiss = { showModal = false }
        )
    }
}

@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun SectionHeader(
    title: String,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        trailing?.invoke()
    }
}


@Preview(showBackground = true)
@Composable
fun FilterSheetPreview() {
    val supplierOpts = listOf(
        Option("sup-abc", "PT. ABC Indonesia"),
        Option("sup-bcd", "PT. BCD Indonesia"),
        Option("sup-opq", "PT. OPQ Indonesia"),
        Option("sup-bcd2", "PT. BCD Indonesia")
    )
    val itemOpts = listOf(
        Option("kecap", "Kecap"),
        Option("airmin", "Air Mineral"),
        Option("kertas", "Kertas HVS"),
        Option("saos", "Saos"),
        Option("laptop", "Laptop")
    )
    val userOpts = listOf(
        Option("pratama", "Pratama"),
        Option("budi", "Budi"),
        Option("john", "John Doe"),
        Option("jane", "Jane Doe"),
        Option("mark", "Mark Lee")
    )

    val specs = listOf(
        OptionsFilterSpec(
            id = "active",
            title = "Active",
            options = listOf(Option("active", "Active"), Option("inactive", "Inactive")),
            limitShown = 2,
            showSeeAll = false
        ),
        OptionsFilterSpec(
            id = "supplier",
            title = "Supplier",
            options = supplierOpts,
            limitShown = 3,
            showSeeAll = true
        ),
        OptionsFilterSpec(
            id = "item",
            title = "Item Name",
            options = itemOpts,
            limitShown = 4,
            showSeeAll = true
        ),
        OptionsFilterSpec(
            id = "modifiedBy",
            title = "Modified by",
            options = userOpts,
            limitShown = 4,
            showSeeAll = true
        ),
        DatePickerFilterSpec(
            id = "lastModified",
            title = "Last Modified"
        )
    )

    // Optional: seed some initial values for preview
    val initial = mapOf(
        "active" to OptionFilterValue(specId = "active", selectedOptions = setOf("active")),
        "supplier" to OptionFilterValue(specId = "supplier", selectedOptions = setOf("sup-abc"))
    )

    LKWangsitTheme(dynamicColor = false) {
        FilterSheet(
            specs = specs,
            initialValues = initial,
            onApply = { /* preview no-op */ },
            onDismissRequest = { /* preview no-op */ },
            onReset = { /* preview no-op */ }
        )
    }
}
