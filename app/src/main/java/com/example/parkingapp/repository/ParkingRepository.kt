package com.example.parkingapp.repository

import androidx.lifecycle.LiveData
import com.example.parkingapp.room.data.ParkingData
import com.example.parkingapp.room.data.User
import com.example.parkingapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ParkingRepository {

//    fun getParkingPlacesStatus(): Flow<List<ParkingData>>

    fun getParkingPlacesStatusLiveData(): LiveData<List<ParkingData>>

    suspend fun updateParkingPlace(place: ParkingData)

    suspend fun registerUser(user: User)

    fun getUserByEmailAndPassword(email: String, password: String): LiveData<User>

    suspend fun addAllParkingPlaces(place: List<ParkingData>)

    fun getAllUsersEmail(): List<String>

    fun getStatusParkingPlaceByEmail(userEmail: String): LiveData<ParkingData>


}