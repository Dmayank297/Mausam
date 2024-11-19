package com.example.mausam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mausam.data.MausamData
import com.example.mausam.ui.screens.HelpScreen
import com.example.mausam.ui.screens.MausamViewModel
import com.example.mausam.ui.screens.Screen
import com.example.mausam.ui.screens.SearchPage
import com.example.mausam.ui.screens.Settings
import com.example.mausam.ui.screens.WeatherHome

@Composable
fun Navigation(
    navController: NavController,
    viewModel: MausamViewModel,
    data: MausamData,
    city: String){

    NavHost(navController = navController as NavHostController,
        startDestination = Screen.HomeScreen.Search_Page.route
    ) {
        composable(Screen.HomeScreen.Search_Page.route) {
            SearchPage()
        }
        
        composable(Screen.HomeScreen.Weather_Home.route) {
            WeatherHome(city = city, data = data, navController = navController as NavHostController)
        }
        
        composable(Screen.DrawerScreen.Help.route)  {
            HelpScreen(navController = navController)
        }
        
        composable(Screen.DrawerScreen.Settings.route) {
            Settings(navController = navController)
        }
        }


}