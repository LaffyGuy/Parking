package com.example.parkingapp.viewmodel

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingapp.repository.ParkingRepository
import com.example.parkingapp.room.data.ParkingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class BookingViewModel @Inject constructor(private val parkingRepository: ParkingRepository): ViewModel() {

    private val _placeInfo = MutableLiveData<ParkingData>()
    val placeInfo: LiveData<ParkingData> = _placeInfo

    fun updateParkingPlace(place: ParkingData){
        viewModelScope.launch(Dispatchers.IO) {
            parkingRepository.updateParkingPlace(place)
            _placeInfo.postValue(place)
            Log.d("MyTag7", "PlaceInfo - $place")
        }
    }

    fun getStatusParkingPlaceByEmail(userEmail: String) = parkingRepository.getStatusParkingPlaceByEmail(userEmail)

}