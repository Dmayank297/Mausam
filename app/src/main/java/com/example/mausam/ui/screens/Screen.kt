package com.example.mausam.ui.screens

import androidx.annotation.DrawableRes
import com.example.mausam.R

sealed class Screen(val route: String, val title: String)  {

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

val screenInDrawer = listOf(
    Screen.DrawerScreen.Help,
    Screen.DrawerScreen.Settings,
)