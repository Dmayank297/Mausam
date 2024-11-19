package com.example.mausam.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mausam.Constant
import com.example.mausam.data.MausamData
import com.example.mausam.network.MausamApi
import com.example.mausam.ui.screens.Screen
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface MausamUiState {
    data class Success(val data: MausamData): MausamUiState
    class Error(val message: String? = null) : MausamUiState
    object Loading: MausamUiState

}



class MausamViewModel: ViewModel() {

    private val _mausamResult = MutableLiveData<MausamUiState>()
    val mausamResult : LiveData<MausamUiState> get()= _mausamResult

    private val apikey = Constant.api_key


    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.DrawerScreen.Help)

    val currentScreen: MutableState<Screen> get() = _currentScreen


    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }

    fun getData(city:String) {
        _mausamResult.value = MausamUiState.Loading
        viewModelScope.launch {

            try {
                val response = MausamApi.retrofitService.getMausam(apikey, city = city)
                _mausamResult.value = MausamUiState.Success(data = response)
            } catch (e: IOException) {
                _mausamResult.value = MausamUiState.Error(message = "Error in Fetching Data!")

            }
        }
    }
}