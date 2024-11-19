package com.example.mausam.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun Settings(
    navController: NavController = rememberNavController()
) {
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = {

                androidx.compose.material.Text(
                    modifier = Modifier
                        .padding(start = 24.dp),
                    text = "Settings Screen",
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,

                    )
            }

        )
    }

    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            androidx.compose.material.Text(text = "Settings Screen")
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    Settings()

}