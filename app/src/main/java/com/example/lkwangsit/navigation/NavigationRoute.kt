package com.example.lkwangsit.navigation

sealed interface  NavigationRoute {
    val route: String

    data object Supplies: NavigationRoute{ override val route: String = "supplies_list" }
}