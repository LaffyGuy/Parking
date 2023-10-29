package com.example.parkingapp.bottomsheetdialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.navArgs
import com.example.parkingapp.databinding.FragmentBottomSheetBinding
import com.example.parkingapp.room.data.ParkingData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetParkingFragment : BottomSheetDialogFragment() {
    lateinit var bindingClass: FragmentBottomSheetBinding
    private val args: BottomSheetParkingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentBottomSheetBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = args.data
        Log.d("MyTag9", "Data Get - $data")

        bindingClass.tvEmailUser.text = data.userEmail
        bindingClass.tvBookingDatePlace.text = data.bookingDate
        bindingClass.tvNumberPlace.text = data.number.toString()


    }


}