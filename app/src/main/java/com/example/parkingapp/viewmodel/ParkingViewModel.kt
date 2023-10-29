package com.example.parkingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingapp.repository.ParkingRepository
import com.example.parkingapp.room.data.ParkingData
import com.example.parkingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ParkingViewModel @Inject constructor(private val parkingRepository: ParkingRepository): ViewModel() {


    private val _parkingStatus = MutableLiveData<Resource<List<ParkingData>>>()
    val parkingStatus: LiveData<Resource<List<ParkingData>>> = _parkingStatus
//
//    private val _parking = MutableStateFlow<Resource<List<ParkingData>>>(Resource.Loading())
//    val parking = _parking.asStateFlow()
//
//    private var job: Job? = null


    fun addAllParkingPlaces(place: List<ParkingData>){
        viewModelScope.launch(Dispatchers.IO) {
            _parkingStatus.postValue(Resource.Loading())
            delay(2000)
            try {
                parkingRepository.addAllParkingPlaces(place)
                _parkingStatus.postValue(Resource.Success(place))
                Log.d("MyTag11", "Load parking places - $place")
            }catch (e: Exception){
                _parkingStatus.postValue(Resource.Error(e.message.toString()))
                Log.d("MyTag3", e.message.toString())
            }

        }
    }

    fun getAllPlacesStatus() = parkingRepository.getParkingPlacesStatusLiveData()

//    fun getAll(){
//        job?.cancel()
//        job = viewModelScope.launch(Dispatchers.IO) {
//        Log.d("MyTag11", "Loading Flow")
//            _parking.value = Resource.Loading()
//            try {
//                parkingRepository.getParkingPlacesStatus().collect{
//                    if(it.isNotEmpty()){
//                        Log.d("MyTag11", "Flow - $it")
//                        _parking.value = Resource.Success(it)
//                    }else{
//                        _parking.value = Resource.Error("List is empty")
//                    }
//                }
//
//            }catch (e: Exception){
//                _parking.value = Resource.Error(e.message.toString())
//            }
//        }
//    }


    fun tryAgain() = parkingRepository.getParkingPlacesStatusLiveData()





}