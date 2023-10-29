package com.example.parkingapp.room.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "parking_table")
data class ParkingData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val number: Int,
    val status: Boolean,
    val bookingDate: String,
    val totalCost: Int,
    val userEmail: String
): Parcelable
