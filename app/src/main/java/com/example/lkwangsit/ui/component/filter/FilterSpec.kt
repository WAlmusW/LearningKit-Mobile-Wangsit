package com.example.lkwangsit.ui.component.filter

enum class Type {
    OPTIONS,
    NUMBER_RANGE,
    DATE_PICKER,
    DATE_RANGE,
}

sealed interface FilterSpec {
    val id: String
    val title: String
    val type: Type
}

data class Option(
    val id: String,
    val label: String,
)

data class OptionsFilterSpec(
    override val id: String,
    override val title: String,
    val options: List<Option>,
    val limitShown: Int = 5,
    val showSeeAll: Boolean = true,
) : FilterSpec { override val type = Type.OPTIONS }

data class NumberRangeFilterSpec(
    override val id: String,
    override val title: String,
    val min: Double? = null,
    val max: Double? = null,
    val step: Double? = null,
) : FilterSpec { override val type = Type.NUMBER_RANGE }

data class DatePickerFilterSpec(
    override val id: String,
    override val title: String,
) : FilterSpec { override val type = Type.DATE_PICKER }

data class DateRangeFilterSpec(
    override val id: String,
    override val title: String,
    val min: Long? = null,
    val max: Long? = null,
) : FilterSpec { override val type = Type.DATE_RANGE }