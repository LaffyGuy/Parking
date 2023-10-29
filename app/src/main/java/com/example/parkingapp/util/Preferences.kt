package com.example.parkingapp.util

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.parkingapp.bottomsheetdialog.BottomSheetParkingFragment
import com.example.parkingapp.bottomsheetdialog.BottomSheetParkingFragmentDirections
import com.example.parkingapp.databinding.FragmentBottomSheetBinding
import com.example.parkingapp.fragments.ParkingFragmentDirections
import com.example.parkingapp.room.data.ParkingData

fun Fragment.navigate(data: ParkingData?, prefEmail: String){
    if(data != null) {
        if (data.userEmail == prefEmail || data.userEmail == "") {
            val action = ParkingFragmentDirections.actionParkingFragmentToBookingFragment(data)
            findNavController().navigate(action)
        } else {
            val action =
                ParkingFragmentDirections.actionParkingFragmentToBottomSheetParkingFragment(data)
            findNavController().navigate(action)
            Log.d("MyTag9", "Data Send - $data")
        }
    }

}

