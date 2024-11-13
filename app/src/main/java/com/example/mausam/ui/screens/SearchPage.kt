package com.example.mausam.ui.screens

import android.util.Log
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mausam.data.MausamData

@Composable
fun SearchPage(
    modifier: Modifier = Modifier,

    ) {

    val viewModel: MausamViewModel = viewModel()
    var city by remember { mutableStateOf("") }
//    val weatherResult by viewModel.weatherResult.collectAsState()
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
                Log.i("city", city)
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
                WeatherCard(city = city, result.data)
            }

            is MausamUiState.Error -> {
                Text(text = "Couldn't fetch the data! Check Internet Connection.")
            }
            null -> {

            }
        }
    }
}

@Composable
fun WeatherCard(
    city : String,
    data: MausamData,

) {
    val context = LocalContext.current
    val navController: NavHostController = NavHostController(context)
    // Make a card that show the weather and when clicked then it navigate to weatherHome Screen
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {

    }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(
            modifier = Modifier
                .clickable {
                    navController.navigate("weatherHome")
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