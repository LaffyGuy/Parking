package com.example.parkingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingapp.repository.ParkingRepository
import com.example.parkingapp.room.data.User
import com.example.parkingapp.util.LoginFieldState
import com.example.parkingapp.util.LoginValidation
import com.example.parkingapp.util.Resource
import com.example.parkingapp.util.validationLoginEmail
import com.example.parkingapp.util.validationLoginPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: ParkingRepository): ViewModel() {

    private val _email = MutableLiveData<Resource<String>>()
    val email: LiveData<Resource<String>> = _email

    private val _password = MutableLiveData<Resource<String>>()
    val password: LiveData<Resource<String>> = _password

    private val _validation = MutableLiveData<LoginFieldState>()
    val validation: LiveData<LoginFieldState> = _validation


     fun loginWithEmailAndPassword(email: String, password: String): LiveData<User>{
        viewModelScope.launch(Dispatchers.IO) {
            if(checkValidation(email, password)){
              loginRepository.getUserByEmailAndPassword(email, password)
            }else{
                val loginFieldState = LoginFieldState(validationLoginEmail(email), validationLoginPassword(password))
                _validation.postValue(loginFieldState)
            }
        }
         return loginRepository.getUserByEmailAndPassword(email, password)
     }


    private fun checkValidation(email: String, password: String): Boolean {
        val emailValidation = validationLoginEmail(email)
        val passwordValidation = validationLoginPassword(password)
        val shouldLogin = emailValidation is LoginValidation.Success && passwordValidation is LoginValidation.Success
        return shouldLogin
    }


}