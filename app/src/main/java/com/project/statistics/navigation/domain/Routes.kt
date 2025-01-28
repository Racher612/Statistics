package com.project.statistics.navigation.domain

sealed class Routes(val route : String) {
    data object Main : Routes("main")
    data object App : Routes("app")

}