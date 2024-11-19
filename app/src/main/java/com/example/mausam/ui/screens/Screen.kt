package com.example.mausam.ui.screens

import androidx.annotation.DrawableRes
import com.example.mausam.R

sealed class Screen(val route: String, val title: String)  {

    sealed class HomeScreen(
        val hRoute: String,
        val hTitle: String
    ):Screen(hRoute,hTitle) {
        data object Search_Page: HomeScreen (
            "search_page",
            "Search_Page"
        )
        data object Weather_Home: HomeScreen (
            "weather_home",
            "Weather_Home"
        )
    }

    sealed class DrawerScreen(val dRoute: String, val dTitle: String, @DrawableRes val Icon: Int
    ): Screen(dRoute, dTitle) {
        object Help: DrawerScreen(
            "help",
            "Help",
            R.drawable.baseline_help_24
        )
        object Settings: DrawerScreen(
            "settings",
            "Settings",
            R.drawable.baseline_settings_24

        )
    }
}

val screenInHome = listOf(
    Screen.HomeScreen.Search_Page,
    Screen.HomeScreen.Weather_Home
)

val screenInDrawer = listOf(
    Screen.DrawerScreen.Help,
    Screen.DrawerScreen.Settings,
)