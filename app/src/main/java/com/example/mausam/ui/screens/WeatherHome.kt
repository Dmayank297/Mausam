package com.example.mausam.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
//    Image(
//        modifier = Modifier,
//        painter = painterResource(id = R.drawable.clear_night_sky_background),
//        contentDescription = null,
//
//    )


    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold (topBar = {
        TopAppBar(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            title = { Text(modifier = Modifier
                .padding(start = 72.dp),
                text = city,
                fontSize = 44.sp
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
                        tint = Color.Black,
                        modifier = Modifier.size(56.dp)
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    // Open a help and setting box
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp))
                }
            }
        )
    }) {
        WeatherDetails(data = data, modifier = Modifier.padding(it))
    }


}

@Composable
fun HomeLayout(data: MausamData,
               city: String) {
    //TODO: Complete the home layout and it will be navigate by weatherCard
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.clear_night_sky_background),
            contentDescription = null )



    }
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
    ),
    MausamData.ForecastData(
        data = "23",
        temperature = "20"
    )
)

@Composable
fun WeatherDetails(data: MausamData, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(vertical = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Column (
            modifier = Modifier
                .padding(vertical = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.Center,
            ){
                Text(
                    modifier = Modifier
                        .padding(),
                    text = data.current.tempC,
                    fontSize = 68.sp
                )
                Text(modifier = Modifier,
                    text = "°C",
                    fontSize = 32.sp,
                    )
            }
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(R.drawable.partly_cloudy_night),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = data.current.cloud,
                fontSize = 16.sp
            )

            Spacer(modifier)
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(
                        elevation = 8.dp, // Set the elevation for the shadow
                        shape = RoundedCornerShape(20), // Use RectangleShape for a horizontal line
                        clip = true // Allow the shadow to extend beyond the bounds of the Divider
                    )
            )




            HorizontalCard(data = data)




        }
    }
}

@Composable
fun HorizontalCard(
    data: MausamData
) {


    Column(
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.windDir}")
                Text(text = "Wind direction")

            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.windKph}Kph")
                Text(text = "Wind speed")
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.dewpoStringC}°C")
                Text(text = "Dew Point")
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.windDegree}°")
                Text(text = "Wind Degree")
            }
        }

        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(
                    elevation = 8.dp, // Set the elevation for the shadow
                    shape = RoundedCornerShape(20), // Use RectangleShape for a horizontal line
                    clip = true // Allow the shadow to extend beyond the bounds of the Divider
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.feelslikeC}°C")
                Text(text = "Feels like")

            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.humidity}%")
                Text(text = "Humidity")
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.pressureMb}hPa")
                Text(text = "Pressure")
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.uv}")
                Text(text = "UV")
            }
        }
    }
}

@Composable
fun SomeInfo(data: MausamData) {
    Text(text = data.current.isDay.toString())
}

@Composable
@Preview(showBackground = true)
fun PreviewWeatherPage() {

     WeatherHome(city = "London", data = sampleMausamData)
    //HomeLayout(data = sampleMausamData, city = "London")
    //HorizontalCard(data = sampleMausamData)
    


}