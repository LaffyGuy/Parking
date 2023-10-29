package com.example.parkingapp.repository

import androidx.lifecycle.LiveData
import com.example.parkingapp.room.DataBase
import com.example.parkingapp.room.data.ParkingData
import com.example.parkingapp.room.data.User
import com.example.parkingapp.util.Resource
import kotlinx.coroutines.flow.Flow


class ParkingRealisation(private val db: DataBase): ParkingRepository {

    override fun getParkingPlacesStatusLiveData(): LiveData<List<ParkingData>> {
        return db.getDao().getParkingPlacesStatusLiveData()
    }

//    override fun getParkingPlacesStatus(): Flow<List<ParkingData>> {
//        return db.getDao().getParkingPlacesStatus()
//    }

    override suspend fun updateParkingPlace(place: ParkingData) {
        db.getDao().updateParkingPlace(place)
    }

    override suspend fun registerUser(user: User) {
        db.getDao().registerUser(user)
    }

    override fun getUserByEmailAndPassword(email: String, password: String): LiveData<User> {
        return db.getDao().getUserByEmailAndPassword(email, password)
    }

    override suspend fun addAllParkingPlaces(place: List<ParkingData>) {
        db.getDao().addAllParkingPlaces(place)
    }

    override fun getAllUsersEmail(): List<String> {
        return db.getDao().getAllUsersEmail()
    }

    override fun getStatusParkingPlaceByEmail(userEmail: String): LiveData<ParkingData> {
        return db.getDao().getStatusParkingPlaceByEmail(userEmail)
    }
}