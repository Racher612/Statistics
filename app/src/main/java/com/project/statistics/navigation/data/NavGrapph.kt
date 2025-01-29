package com.project.statistics.navigation.data

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.statistics.main.pres.MainScreen
import com.project.statistics.navigation.domain.Routes

@Composable
fun NavGraph(
    navController : NavHostController
){
    NavHost(navController, startDestination = Routes.Main.route){
        composable(Routes.Main.route) {
            MainScreen()
        }
        composable(Routes.App.route) {

        }
    }
}