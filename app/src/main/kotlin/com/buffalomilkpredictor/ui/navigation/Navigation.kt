package com.buffalomilkpredictor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buffalomilkpredictor.ui.screens.home.HomeScreen
import com.buffalomilkpredictor.ui.screens.capture.CaptureScreen
import com.buffalomilkpredictor.ui.screens.analysis.AnalysisScreen
import com.buffalomilkpredictor.ui.screens.result.ResultScreen
import com.buffalomilkpredictor.ui.screens.history.HistoryScreen
import com.buffalomilkpredictor.ui.screens.settings.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.Home.route
    ) {
        composable(NavigationScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(NavigationScreen.Capture.route) {
            CaptureScreen(navController = navController)
        }
        
        composable(NavigationScreen.Analysis.route) {
            AnalysisScreen(navController = navController)
        }
        
        composable(NavigationScreen.Result.route + "/{resultId}") { backStackEntry ->
            val resultId = backStackEntry.arguments?.getString("resultId")?.toIntOrNull() ?: 0
            ResultScreen(navController = navController, resultId = resultId)
        }
        
        composable(NavigationScreen.History.route) {
            HistoryScreen(navController = navController)
        }
        
        composable(NavigationScreen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}

sealed class NavigationScreen(val route: String) {
    object Home : NavigationScreen("home")
    object Capture : NavigationScreen("capture")
    object Analysis : NavigationScreen("analysis")
    object Result : NavigationScreen("result")
    object History : NavigationScreen("history")
    object Settings : NavigationScreen("settings")
}
