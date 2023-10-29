package com.example.parkingapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.parkingapp.ParkingActivity
import com.example.parkingapp.R
import com.example.parkingapp.databinding.FragmentRegisterBinding
import com.example.parkingapp.room.data.User
import com.example.parkingapp.util.Constance
import com.example.parkingapp.util.RegisterValidation
import com.example.parkingapp.util.Resource
import com.example.parkingapp.viewmodel.ParkingViewModel
import com.example.parkingapp.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    lateinit var bindingClass: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by activityViewModels()
    private lateinit var allUsersEmailList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentRegisterBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        registerViewModel.getAllUsersEmail().observe(viewLifecycleOwner){
//            allUsersEmailList = it
//            Log.d("MyTag5", "info - $allUsersEmailList")
//        }

        bindingClass.btnRegister.setOnClickListener {
            val isEmailInList = registerViewModel.isEmailInList(bindingClass.etEmail.text.toString())
            Log.d("MyTag", "isEmailInList - $isEmailInList")
            Log.d("MyTag5", "Edit text email - ${bindingClass.etEmail.text}")
            if(isEmailInList){
                Log.d("MyTag5", "True")
                Toast.makeText(requireContext(), "Користувач з такою поштою вже зареєстрований", Toast.LENGTH_SHORT).show()
            }else{
                registerUser()
                Log.d("MyTag5", "False")
            }
        }

        observeUser()

        observeValidation()



    }

    private fun registerUser(){
        val user = User(null,
        bindingClass.etFirstName.text.toString(),
        bindingClass.etLastName.text.toString(),
        bindingClass.etEmail.text.toString(),
        bindingClass.etPassword.text.toString()
        )

        registerViewModel.registerUser(user)

        val pref = requireContext().getSharedPreferences(Constance.PREF_TABLE_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(Constance.PREF_EMAIL_NAME, bindingClass.etEmail.text.toString()).apply()
        Log.d("MyTag4", "Pref ID - ${bindingClass.etEmail.text}")
    }

    private fun observeUser(){
        registerViewModel.user.observe(viewLifecycleOwner){
            Log.d("MyTag5", "User data - ${it.data}")
            when(it){
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    Log.d("MyTag1", "Success")

                        val intent = Intent(requireContext(), ParkingActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("key", it.data?.firstName)
                        startActivity(intent)
                    }
                is Resource.Error -> {
                    Log.d("MyTag", "Error user - ${it.message.toString()}")
                }
            }
        }
    }


    private fun observeValidation(){
        registerViewModel.validation.observe(viewLifecycleOwner){
            if(it.firstName is RegisterValidation.Failed){
                bindingClass.etFirstName.apply {
                    requestFocus()
                    error = it.firstName.message
                }
            }
            if(it.lastName is RegisterValidation.Failed){
                bindingClass.etLastName.apply {
                    requestFocus()
                    error = it.lastName.message
                }
            }
            if(it.email is RegisterValidation.Failed){
                bindingClass.etEmail.apply {
                    requestFocus()
                    error = it.email.message
                }
            }
            if(it.password is RegisterValidation.Failed){
                bindingClass.etPassword.apply {
                    requestFocus()
                    error = it.password.message
                }
            }

        }

    }
    

}