package com.example.mausam.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mausam.ui.screens.HelpScreen
import com.example.mausam.ui.screens.Screen
import com.example.mausam.ui.screens.SearchPage
import com.example.mausam.ui.screens.Settings
import com.example.mausam.ui.screens.WeatherHome

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,

){

    val viewModelStore = LocalViewModelStoreOwner.current!!.viewModelStore

    navController.setViewModelStore(viewModelStore)

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.Search_Page.route
    ) {
        composable(Screen.HomeScreen.Search_Page.route) {
            SearchPage(navController = navController)
        }
        
        composable(Screen.HomeScreen.Weather_Home.route + "/{city}",
            arguments = listOf(navArgument("city") {type = NavType.StringType})
        ) { backStackEntry ->
            val citY = backStackEntry.arguments?.getString("city") ?: ""
            WeatherHome(city = citY, navController = navController)
        }
        
        composable(Screen.DrawerScreen.Help.route)  {
            HelpScreen(navController = navController)
        }
        
        composable(Screen.DrawerScreen.Settings.route) {
            Settings(navController = navController)
        }
        }


}