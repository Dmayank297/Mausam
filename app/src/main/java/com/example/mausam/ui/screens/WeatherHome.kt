package com.example.mausam.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mausam.R
import com.example.mausam.data.MausamData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHome(
    city: String,
    data: MausamData,
    onBackNavClicked: () -> Unit = {}
) {


    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()


    Scaffold (topBar = {
        TopAppBar(title = {
            Text(text = city,
                ) },
            navigationIcon = {
                IconButton(onClick = {
                    // Navigate to search city screen
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location_city_24),
                        contentDescription = "City-Location",
                        tint = Color.Black
                    )
                }

            },
        )

    })
    {

        Text(text = "", modifier = Modifier.padding(it))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = data.current.tempC)
            Text(text = data.current.cloud)

        }
    }
}

@Composable
fun HomeLayout(data: MausamData,
               city: String) {
    //TODO: Complete the home layout and it will be navigate by weatherCard
}


val sampleMausamData = MausamData(
    id = 1, // You can set any ID here
    current = MausamData.Current(
        id = 1, // You can set any ID here
        cloud = "Partly cloudy",
        condition = MausamData.Current.Condition(
            code = "1003",
            icon = "//cdn.weatherapi.com/weather/64x64/day/116.png",
            text = "Partly cloudy"
        ),
        dewpoStringC = "10",
        dewpoStringF = "50",
        feelslikeC = "22",
        feelslikeF = "72",
        gustKph = "15",
        gustMph = "9",
        heatindexC = "24",
        heatindexF = "75",
        humidity = "60",
        isDay = "1",
        lastUpdated = "2023-12-18 14:00",
        lastUpdatedEpoch = "1702905600",
        precipIn = "0.0",
        precipMm = "0.0",
        pressureIn = "30.1",
        pressureMb = "1019",
        tempC = "20",
        tempF = "68",
        uv = "5",
        visKm = "10",
        visMiles = "6",
        windDegree = "180",
        windDir = "S",
        windKph = "10",
        windMph = "6",
        windchillC = "18",
        windchillF = "64"
    ),
    location = MausamData.Location(
        country = "India",
        lat = "28.6139",
        localtime = "2023-12-18 19:30",
        localtimeEpoch = "1702927400",
        lon = "77.2090",
        name = "Delhi",
        region = "Delhi",
        tzId = "Asia/Kolkata"
    )
)

@Composable
@Preview(showBackground = true)
fun PreviewWeatherPage() {

    WeatherHome(city = "London", data = sampleMausamData)

}