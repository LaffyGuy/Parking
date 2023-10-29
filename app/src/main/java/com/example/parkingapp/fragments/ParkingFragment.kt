package com.example.parkingapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.parkingapp.R
import com.example.parkingapp.bottomsheetdialog.BottomSheetParkingFragment
import com.example.parkingapp.databinding.FragmentParkingBinding
import com.example.parkingapp.databinding.PartResultBinding
import com.example.parkingapp.room.data.ParkingData
import com.example.parkingapp.util.Constance
import com.example.parkingapp.util.Resource
import com.example.parkingapp.util.navigate
import com.example.parkingapp.viewmodel.ParkingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParkingFragment : Fragment() {
    lateinit var bindingClass: FragmentParkingBinding
    lateinit var partBinding: PartResultBinding
    private val parkingViewModel: ParkingViewModel by viewModels()
    lateinit var pref: SharedPreferences
    private var prefEmail = ""
    private var listParkingPlaces = listOf(
        ParkingData(null, 1, false, "", 0, ""),
        ParkingData(null, 2, false, "", 0, ""),
        ParkingData(null, 3, false, "", 0, ""),
        ParkingData(null, 4, false, "", 0, ""),
        ParkingData(null, 5, false, "", 0, ""),
        ParkingData(null, 6, false, "", 0, ""),
        ParkingData(null, 7, false, "", 0, ""),
        ParkingData(null, 8, false, "", 0, ""),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentParkingBinding.inflate(layoutInflater)
        partBinding = PartResultBinding.bind(bindingClass.root)
        return bindingClass.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("table", Context.MODE_PRIVATE)

        val isLoaded = sharedPref.getBoolean("key", false)

        if(!isLoaded){
            parkingViewModel.addAllParkingPlaces(listParkingPlaces)
            observeAllParkingPlaces()
            sharedPref.edit().putBoolean("key", true).apply()
        }

        pref = requireContext().getSharedPreferences(Constance.PREF_TABLE_NAME, Context.MODE_PRIVATE)
        prefEmail = pref.getString(Constance.PREF_EMAIL_NAME, "").toString()
        Log.d("MyTag8", "PrefEmail = $prefEmail")

        getAllParkingPlacesStatus()

    }

    private fun getAllParkingPlacesStatus(){
        Log.d("MyTag11", "getAllParkingPlacesStatus")
        parkingViewModel.getAllPlacesStatus().observe(viewLifecycleOwner){ result ->
                Log.d("MyTag11", "LiveData List - $result")
                bindingClass.firstPlace.setOnClickListener {
                    val data = result?.get(0)
                    Log.d("MyTag4", "Data - $data")
                  navigate(data!!, prefEmail)
                }

                bindingClass.secondPlace.setOnClickListener {
                    val data = result?.get(1)
                    navigate(data!!, prefEmail)
                }

                bindingClass.thirdPlace.setOnClickListener {
                    val data = result?.get(2)
                    navigate(data!!, prefEmail)
                }

                bindingClass.fourthPlace.setOnClickListener {
                    val data = result?.get(3)
                    navigate(data!!, prefEmail)
                }

                bindingClass.fifthPlace.setOnClickListener {
                    val data = result?.get(4)
                    navigate(data!!, prefEmail)
                }

                bindingClass.sixthPlace.setOnClickListener {
                    val data = result?.get(5)
                    navigate(data!!, prefEmail)
                }

                bindingClass.seventhPlace.setOnClickListener {
                    val data = result?.get(6)
                    navigate(data!!, prefEmail)
                }

                bindingClass.eightPlace.setOnClickListener {
                    val data = result?.get(7)
                    navigate(data!!, prefEmail)
                }
                result!!.forEachIndexed { index, item ->
                    if(item.status == true){
                        when(index){
                            0 -> setColor(0, item.userEmail)
                            1 -> setColor(1, item.userEmail)
                            2 -> setColor(2, item.userEmail)
                            3 -> setColor(3, item.userEmail)
                            4 -> setColor(4, item.userEmail)
                            5 -> setColor(5, item.userEmail)
                            6 -> setColor(6, item.userEmail)
                            7 -> setColor(7, item.userEmail)
                        }
                    }
                }
        }
    }



    private fun setColor(place: Int, userEmail: String){
      val view: View = when(place){
          0 -> bindingClass.firstPlace
          1 -> bindingClass.secondPlace
          2 -> bindingClass.thirdPlace
          3 -> bindingClass.fourthPlace
          4 -> bindingClass.fifthPlace
          5 -> bindingClass.sixthPlace
          6 -> bindingClass.seventhPlace
          7 -> bindingClass.eightPlace
          else -> return
      }

        val backgroundColor = if(userEmail == prefEmail) AppCompatResources.getDrawable(requireContext(), R.drawable.parking_view_my_style)
        else AppCompatResources.getDrawable(requireContext(), R.drawable.parking_view_another_style)
        view.background = backgroundColor
    }

    private fun observeAllParkingPlaces(){
            parkingViewModel.parkingStatus.observe(viewLifecycleOwner){ result ->
                Log.d("MyTag11", "observeAllParkingPlaces")
                when(result){
                    is Resource.Loading -> {
                        Log.d("MyTag11", "Loading")
                        bindingClass.mainLinearLayout.visibility = View.INVISIBLE
                        partBinding.linearLayout.visibility = View.GONE
                        partBinding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        Log.d("MyTag11", "Success")
                        if(result.data?.isEmpty() == true){
                            Log.d("MyTag11", "List empty - ${result.data}")
                            partBinding.progressBar.visibility = View.GONE
                            partBinding.linearLayout.visibility = View.GONE
                            bindingClass.tvEmptyList.visibility = View.VISIBLE
                            bindingClass.mainLinearLayout.visibility = View.GONE

                        }else{
                            Log.d("MyTag11", "List inNotEmpty - ${result.data}")
                            partBinding.progressBar.visibility = View.GONE
                            partBinding.linearLayout.visibility = View.GONE
                            bindingClass.mainLinearLayout.visibility = View.VISIBLE
                        }

                    }
                    is Resource.Error -> {
                        bindingClass.mainLinearLayout.visibility = View.GONE
                        partBinding.progressBar.visibility = View.GONE
                        partBinding.linearLayout.visibility = View.VISIBLE
                        Log.d("MyTag11", "Error")
                        Log.d("MyTag3", result.message.toString())
                    }
                }
            }

    }
}