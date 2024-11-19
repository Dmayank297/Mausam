package com.example.mausam

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mausam.data.MausamData
import com.example.mausam.navigation.Navigation
import com.example.mausam.network.MausamApi
import com.example.mausam.ui.screens.SearchPage
import com.example.mausam.ui.screens.WeatherHome
import com.example.mausam.ui.screens.sampleMausamData
import com.example.mausam.ui.theme.MausamTheme

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MausamTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)

            }
        }
    }
}



