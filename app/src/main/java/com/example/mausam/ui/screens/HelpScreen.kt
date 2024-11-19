package com.example.mausam.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.mausam.R

@Composable
fun HelpScreen(
    navController: NavController
) {

}

@Preview(showBackground = false)
@Composable
fun previewImage() {
    val context = LocalContext.current
    val navController: NavController = NavController(context = context)
    HelpScreen(navController = navController)
}