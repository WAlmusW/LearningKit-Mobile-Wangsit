package com.example.lkwangsit.ui.component.filter

sealed interface FilterValue {
    val specId: String
}

data class OptionFilterValue(
    override val specId: String,
    val selectedOptions: Set<String> = emptySet(),
) : FilterValue

fun <T> Set<T>.toggled(item: T): Set<T> =
    if (item in this) this - item else this + item

data class NumberRangeFilterValue(
    override val specId: String,
    val from: Double? = null,
    val to: Double? = null,
) : FilterValue

data class DatePickerFilterValue(
    override val specId: String,
    val dateMillis: Long? = null,
) : FilterValue

data class DateRangeFilterValue(
    override val specId: String,
    val startMillis: Long? = null,
    val endMillis: Long? = null,
) : FilterValue