package com.example.lkwangsit.navigation

sealed interface  NavigationRoute {
    val route: String

    data object SupplierList: NavigationRoute{ override val route: String = "supplier_list" }
}