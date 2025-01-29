package com.project.statistics.navigation.domain

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.statistics.R
import com.project.statistics.app.pres.AppScreen
import com.project.statistics.main.pres.MainScreen
import com.project.statistics.navigation.domain.models.Routes

@Composable
fun NavGraph(
    navController : NavHostController
) {
    val param = stringResource(R.string.param)
    val navigateToApp: (String) -> Unit = {route ->
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToMain: () -> Unit = {
        navController.navigate(Routes.Main.route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(navController, startDestination = Routes.Main.route){
        composable(Routes.Main.route) {
            MainScreen(
                navigateToApp
            )
        }
        composable(
            route = Routes.App.route + Routes.Param.route,
            arguments = listOf(navArgument(param){ type = NavType.StringType})
        ) {backStackEntry ->
            val packageName = backStackEntry.arguments?.getString(param) ?: ""
            AppScreen(
                packageName = packageName,
                navigateBack = navigateToMain
            )
        }
    }
}
