package com.example.libs.data.source.network.model.request.supply

data class GetSuppliesQueryParams(
    val search: String? = null,
    val supplier: List<String>? = null,
    val city: List<String>? = null,
    val itemName: List<String>? = null,
    val modifiedBy: String? = null,
    val page: Number? = null,
    val limit:Number? = null,
    val sortOrder: Number? = null,
    val sortBy: String? = null,
) {
    fun toQueryMap(): Map<String, String?>{
        return mapOf(
            "search" to search,
            "supplier" to supplier?.joinToString(","),
            "city" to city?.joinToString(","),
            "itemName" to itemName?.joinToString(","),
            "modifiedBy" to modifiedBy,
            "page" to page.toString(),
            "limit" to limit.toString(),
            "sortOrder" to sortOrder.toString(),
            "sortBy" to sortBy,
        ).filterValues { it != null }
    }
}