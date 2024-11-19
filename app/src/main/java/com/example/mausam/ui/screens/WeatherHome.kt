package com.example.mausam.ui.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mausam.R
import com.example.mausam.data.MausamData
import com.example.mausam.model.MausamUiState
import com.example.mausam.model.MausamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHome(
    city: String,

    navController: NavHostController
) {
    val viewModel: MausamViewModel = viewModel()


    val data by viewModel.mausamResult.observeAsState(MausamUiState.Loading)
        LaunchedEffect(city) {
        viewModel.getData(city)
    }

   var currentTime by remember { mutableIntStateOf(LocalTime.now().hour) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now().hour
            delay(60000) // every minute
        }
    }
    val imageResource = remember(currentTime) {
        if(currentTime in 6..18) {
            R.drawable.day_sky
        } else {
            R.drawable.night_sky

        }
    }

    // Allow us to find out on which "View or page" we current are
    val controller: NavController = rememberNavController()
    val navBackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackEntry?.destination?.route


    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()



     Scaffold(topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = city,
                        fontSize = 44.sp,
                        color = Color.Unspecified,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // Navigate to search city screen
                            scope.launch {
                                // Navigate back to search screen
                                navController.navigate(Screen.HomeScreen.Search_Page.hRoute) {
                                    popUpTo(Screen.HomeScreen.Weather_Home.hRoute) {inclusive = true}
                                }
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(Color.White)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location_city_24),
                            contentDescription = "City-Location",
                            tint = Color.Black,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Open a help and setting box
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        },
            scaffoldState = scaffoldState,
            drawerContent = {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(screenInDrawer) { item ->
                        Drawer(selected = currentRoute == item.dRoute, item = item) {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                             navController.navigate(item.dRoute)

                        }
                    }
                }
            }

        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "Dynamic Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

                // Show loading or the actual data
                when (val uiState = data) {
                    is MausamUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.fillMaxSize()) // Show loader while data is loading
                    }
                    is MausamUiState.Success -> {
                        // Display weather data once it's loaded
                        LazyColumn (modifier = Modifier.fillMaxSize()) {
                            item {
                                WeatherDetails(data = uiState.data, modifier = Modifier.padding(it))
                            }
                        }

                    }
                    is MausamUiState.Error -> {
                        Text("Error: ${uiState.message}") // Display an error message if the data fetching fails
                    }
                }
            }
    }
}

@Composable
fun HomeLayout(data: MausamData,
               city: String) {
    // Complete the home layout and it will be navigate by weatherCard

}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherDetails(
    data: MausamData,
    modifier: Modifier = Modifier
) {

    var currentTime by remember { mutableIntStateOf(LocalTime.now().hour) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now().hour
            delay(60000) // every minute
        }
    }
    val color = remember(currentTime) {
        if(currentTime in 6..18) {
            Color.Black
        } else {
            Color.White
        }
    }

    val cloud = remember() {
        when(data.current.cloud) {
            0 -> "Sunny"
            in 1..20 -> "Mostly Sunny"
            in 21..50 -> "Partly Cloudy"
            in 51..85 -> "Mostly Cloudy"
            in 86..100 -> "Overcast"
            else -> "Unknown"
        }
    }

//    val rain = when(data.current.precipMm) {
//         in 0..2 -> "Very light rain"
//         in 2..5 -> "Light rain"
//         in 5..10 -> "Moderate rain"
//         in 11.. 49 -> "Heavy rain"
//        50 -> "Extremely heavy rain (common in storms or tropical weather)."
//        else -> "Unknown"
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(vertical = 72.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Column (
            modifier = Modifier
                .padding(vertical = 72.dp),
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
                    fontSize = 88.sp,
                    color = color
                )
                Text(modifier = Modifier.padding(),
                    text = "°C",
                    fontSize = 32.sp,
                    color = Color.DarkGray
                    )
            }
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(R.drawable.partly_cloudy_night),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = cloud,
                fontSize = 36.sp,
                color = color
            )

            Spacer(modifier = Modifier.weight(1f))
            Divider(
                color = Color.LightGray,
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
fun Drawer(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit
) {
    val background = if (selected) Color.DarkGray else Color.White
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .background(background)
            .clickable {
                onDrawerItemClicked()
            }) {


        Icon(
            painter = painterResource(id = item.Icon),
            contentDescription = item.dTitle,
            Modifier
                .padding(top = 24.dp)
                .size(28.dp)
        )
        Text(modifier = Modifier.padding(start = 8.dp ,top = 20.dp),
            text = item.dTitle,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorizontalCard(
    data: MausamData
) {

    val currentTime = LocalTime.now().hour
    val color = if (currentTime in 6..18) Color.Black else Color.White


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
                Text(text = "${data.current.windDir}",color = color)
                Text(text = "Wind direction",color = color)

            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.windKph}Kph" ,color = color)
                Text(text = "Wind speed",color = color)
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.precipMm}",color = color)
                Text(text = "Precip",color = color)
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.windDegree}°",color = color)
                Text(text = "Wind Degree",color = color)
            }
        }

        Divider(
            color = Color.LightGray,
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
                Text(text = "${data.current.feelslikeC}°C",color = color)
                Text(text = "Feels like", color = color)

            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.humidity}%",color = color)
                Text(text = "Humidity",color = color)
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.pressureMb}hPa",color = color)
                Text(text = "Pressure",color = color)
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.current.uv}",color = color)
                Text(text = "UV",color = color)
            }
        }
    }
}



@Composable
fun SomeInfo(data: MausamData) {
    Text(text = data.current.isDay.toString())
}



val sampleMausamData = MausamData(
    id = 1, // You can set any ID here
    current = MausamData.Current(
        id = 1, // You can set any ID here
        cloud = 45,
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
        precipMm = 0.0,
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun PreviewWeatherPage() {
//    val navController: NavController = rememberNavController()
//     WeatherHome(city = "London", data = sampleMausamData)
   // HomeLayout(data = sampleMausamData, city = "London")
    //HorizontalCard(data = sampleMausamData)
    


}