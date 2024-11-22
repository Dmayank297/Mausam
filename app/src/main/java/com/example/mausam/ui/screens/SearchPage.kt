package com.example.mausam.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mausam.data.MausamData
import com.example.mausam.model.MausamUiState
import com.example.mausam.model.MausamViewModel


@Composable
fun SearchPage(
    modifier: Modifier = Modifier,
    navController: NavHostController
    ) {
    var city by remember { mutableStateOf("") }
    val viewModel: MausamViewModel = viewModel()
    val weatherResult = viewModel.mausamResult.observeAsState()


    Column (
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxSize(1f)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.padding(top = 36.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                value = city,
                onValueChange = {
                    city = it
                },
                label = {
                    Text(text = "Enter City Name")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location")
                },
                shape = RoundedCornerShape(16.dp)
            )

            IconButton(onClick = {
                // Call viewModel to fetch city data
                viewModel.getData(city)

            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search for location")
            }
        }

        when(val result = weatherResult.value) {
            is MausamUiState.Loading -> {
                CircularProgressIndicator()
            }
            is MausamUiState.Success -> {
                WeatherCard(city = city, result.data, navController)
            }

            is MausamUiState.Error -> {
                Text(text = "Couldn't fetch the data! Check Internet Connection.")
            }
            null -> {
                Text(text = "Search The City!",
                    color = Color.Magenta,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }



}


@Composable
fun WeatherCard(
    city : String,
    data: MausamData,
    navController: NavHostController
) {

    // Make a card that show the weather and when clicked then it navigate to weatherHome Screen
Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(
            modifier = Modifier
                .clickable {
                    // Navigate to WeatherScreen
                    navController.navigate(Screen.HomeScreen.Weather_Home.hRoute + "/$city")
                }
                .fillMaxWidth()
                .padding(18.dp)
                .border(2.dp, color = Color.Black, RoundedCornerShape(40))
                .size(60.dp)
                .align(alignment = Alignment.End),
            elevation = CardDefaults.cardElevation(hoveredElevation = 30.dp),
            colors = CardDefaults.cardColors(Color.Cyan)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(30.dp),

                    )

                Spacer(modifier = Modifier.width(8.dp))
                Text(text = data.location.name, fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = data.location.country)
                Spacer(modifier = Modifier.width(40.dp))
                Text(text = "${data.current.tempC} ° C", fontSize = 20.sp, fontFamily = FontFamily.Serif)
            }
        }

    }
}