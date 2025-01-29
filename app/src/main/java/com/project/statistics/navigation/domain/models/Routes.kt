package com.project.statistics.navigation.domain.models

sealed class Routes(val route : String) {
    data object Main : Routes("main")
    data object App : Routes("app")
    data object Param : Routes("/{param}")

}