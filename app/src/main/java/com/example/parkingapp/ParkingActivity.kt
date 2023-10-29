package com.example.parkingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.parkingapp.databinding.ActivityParkingBinding
import com.example.parkingapp.fragments.ParkingFragment
import com.example.parkingapp.util.Constance.ACTION_SHOW_PARKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParkingActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityParkingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityParkingBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }


}