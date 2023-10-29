package com.example.parkingapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parkingapp.room.data.ParkingData
import com.example.parkingapp.room.data.User
import com.example.parkingapp.util.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingDao {

//    @Query("SELECT * FROM parking_table")
//    fun getParkingPlacesStatus(): Flow<List<ParkingData>>

    @Query("SELECT * FROM parking_table")
    fun getParkingPlacesStatusLiveData(): LiveData<List<ParkingData>>

    @Update
    suspend fun updateParkingPlace(place: ParkingData)

    @Insert
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllParkingPlaces(place: List<ParkingData>)

    @Query("SELECT email FROM user_table")
    fun getAllUsersEmail(): List<String>

    @Query("SELECT * FROM parking_table WHERE userEmail = :userEmail")
    fun getStatusParkingPlaceByEmail(userEmail: String): LiveData<ParkingData>

}