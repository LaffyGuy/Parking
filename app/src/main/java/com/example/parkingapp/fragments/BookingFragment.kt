package com.example.parkingapp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.parkingapp.R
import com.example.parkingapp.databinding.FragmentBookingBinding
import com.example.parkingapp.room.data.ParkingData
import com.example.parkingapp.services.ParkingService
import com.example.parkingapp.util.Constance
import com.example.parkingapp.util.Constance.MILLIS_TO_SERVICE
import com.example.parkingapp.util.Constance.NUMBER_TO_SERVICE
import com.example.parkingapp.util.Constance.PENDING_INTENT_NUMBER
import com.example.parkingapp.util.SelectTime
import com.example.parkingapp.viewmodel.BookingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class BookingFragment : Fragment() {
    lateinit var bindingClas: FragmentBookingBinding
    private val bookingViewModel: BookingViewModel by viewModels()
    private val args: BookingFragmentArgs by navArgs()
    private var userEmail = ""
    private var timeInMillis: Long? = null
    private var number = 0
    private val selectTime = SelectTime()
    private val parkingService = ParkingService
    private var statusOfParkingPlace: ParkingData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClas = FragmentBookingBinding.inflate(layoutInflater)
        return bindingClas.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = requireActivity().getSharedPreferences(Constance.PREF_TABLE_NAME, Context.MODE_PRIVATE)
        userEmail = pref.getString(Constance.PREF_EMAIL_NAME, null).toString()
        Log.d("MyTag4", "Pref userEmail - $userEmail")

        getData()

        observeServiceLiveData()

        getTodayDateAndTime()

        observePlaceInfo()

        bindingClas.cvDate.setOnClickListener {
            selectTime.getTotalHoursBookingParkingPlace1(requireContext()){ date, timeMilliSeconds, _, totalCost ->
                updateUi(date, totalCost)
                timeInMillis = timeMilliSeconds
            }
        }

        bookingViewModel.getStatusParkingPlaceByEmail(userEmail).observe(viewLifecycleOwner){
            statusOfParkingPlace = it
        }

        bindingClas.btnBooking.setOnClickListener {
            if(statusOfParkingPlace == null){
                if(timeInMillis!! >= 3600000) {
                    updateParkingPlace()
                    sendCommandToService()
                }else{
                    Toast.makeText(requireContext(), "Мінімальний час паркування - 1 година", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), "Можна забронювати тільки одне паркомісце", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getData(){
        val data = args.parkingData
        bindingClas.apply {
            tvNumberPlace.text = data.number.toString()
            tvParkingDate.text = data.bookingDate
            tvTotalCost.text = "${data.totalCost}₴"
            Log.d("MyTag20", "TvDate - $data.")
        }
        number = data.number
    }

    private fun updateParkingPlace(){
       bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, true, selectTime.bookingDate, selectTime.totalCost, userEmail))
       Log.d("MyTag4", "User email = $userEmail")
       findNavController().navigate(R.id.action_bookingFragment_to_parkingFragment)

    }



    private fun sendCommandToService(){
        val intent = Intent(requireContext(), ParkingService::class.java)
        intent.action = ParkingService.Action.START.toString()
        intent.putExtra(MILLIS_TO_SERVICE, timeInMillis)
        intent.putExtra(NUMBER_TO_SERVICE, number)
        requireContext().startService(intent)
        Log.d("MyTag20", "Send time to service - $timeInMillis")
    }

    private fun observePlaceInfo(){
        bookingViewModel.placeInfo.observe(viewLifecycleOwner){
            Log.d("MyTag7", "Observe place info - $it")
        }
    }

    private fun getTodayDateAndTime(){
            val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss" , Locale.getDefault())

            val dateString = date.format(Date())

            bindingClas.tvTodayDate.text = dateString
        }

    private fun updateUi(bookingDate: String, totalCost: Int){
        bindingClas.tvParkingDate.text = bookingDate
        bindingClas.tvTotalCost.text = "$totalCost₴"
    }

    private fun observeServiceLiveData(){
        when(args.parkingData.number){
            1 -> {
                parkingService.timerLiveData1.observe(viewLifecycleOwner){
                    bindingClas.tvTimer.text = String.format("%02d:%02d", it.first, it.second)
                    Log.d("MyTag10", "Fragment count livedata - $it")
                    if(it == Pair(0L, 0L)){
                        bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, false, "", 0, ""))
                    }
                }
            }
            2 ->{
                parkingService.timerLiveData2.observe(viewLifecycleOwner){
                    bindingClas.tvTimer.text = String.format("%02d:%02d", it.first, it.second)
                    Log.d("MyTag10", "Fragment count livedata - $it")
                    if(it == Pair(0L, 0L)){
                        bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, false, "", 0, ""))
                    }
                }
            }

            3 -> {
                parkingService.timerLiveData3.observe(viewLifecycleOwner){
                    bindingClas.tvTimer.text = String.format("%02d:%02d", it.first, it.second)
                    Log.d("MyTag10", "Fragment count livedata - $it")
                    if(it == Pair(0L, 0L)){
                        bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, false, "", 0, ""))
                    }
                }
            }
            4 -> {
                parkingService.timerLiveData4.observe(viewLifecycleOwner){
                    bindingClas.tvTimer.text = String.format("%02d:%02d", it.first, it.second)
                    Log.d("MyTag10", "Fragment count livedata - $it")
                    if(it == Pair(0L, 0L)){
                        bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, false, "", 0, ""))
                    }
                }
            }
            5 -> {
                parkingService.timerLiveData5.observe(viewLifecycleOwner){
                    bindingClas.tvTimer.text = String.format("%02d:%02d", it.first, it.second)
                    Log.d("MyTag10", "Fragment count livedata - $it")
                    if(it == Pair(0L, 0L)){
                        bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, false, "", 0, ""))
                    }
                }
            }
            6 -> {
                parkingService.timerLiveData6.observe(viewLifecycleOwner){
                    bindingClas.tvTimer.text = String.format("%02d:%02d", it.first, it.second)
                    Log.d("MyTag10", "Fragment count livedata - $it")
                    if(it == Pair(0L, 0L)){
                        bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, false, "", 0, ""))
                    }
                }
            }
            7 -> {
                parkingService.timerLiveData7.observe(viewLifecycleOwner){
                    bindingClas.tvTimer.text = String.format("%02d:%02d", it.first, it.second)
                    Log.d("MyTag10", "Fragment count livedata - $it")
                    if(it == Pair(0L, 0L)){
                        bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, false, "", 0, ""))
                    }
                }
            }
            8 -> {
                parkingService.timerLiveData8.observe(viewLifecycleOwner){
                    bindingClas.tvTimer.text = String.format("%02d:%02d", it.first, it.second)
                    Log.d("MyTag10", "Fragment count livedata - $it")
                    if(it == Pair(0L, 0L)){
                        bookingViewModel.updateParkingPlace(ParkingData(args.parkingData.id, args.parkingData.number, false, "", 0, ""))
                    }
                }
            }
        }
    }
}
