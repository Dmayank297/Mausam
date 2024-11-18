package com.example.mausam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mausam.ui.screens.WeatherDetails
import com.example.mausam.ui.screens.WeatherHome
import com.example.mausam.ui.screens.sampleMausamData
import com.example.mausam.ui.theme.MausamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MausamTheme {
                //SearchPage()
                WeatherHome(city = "London", data = sampleMausamData)
            }
        }
    }
}

