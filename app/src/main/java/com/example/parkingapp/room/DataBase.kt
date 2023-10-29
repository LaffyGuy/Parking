package com.example.parkingapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parkingapp.room.dao.ParkingDao
import com.example.parkingapp.room.data.ParkingData
import com.example.parkingapp.room.data.User


@Database(entities = [ParkingData::class, User::class], version = 8)
abstract class DataBase: RoomDatabase() {

     abstract fun getDao(): ParkingDao


}