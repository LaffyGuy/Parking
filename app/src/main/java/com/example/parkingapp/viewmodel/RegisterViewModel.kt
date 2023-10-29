package com.example.parkingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingapp.repository.ParkingRepository
import com.example.parkingapp.room.data.User
import com.example.parkingapp.util.RegisterFieldState
import com.example.parkingapp.util.RegisterValidation
import com.example.parkingapp.util.Resource
import com.example.parkingapp.util.validationEmail
import com.example.parkingapp.util.validationFirstName
import com.example.parkingapp.util.validationLastName
import com.example.parkingapp.util.validationPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val parkingRepository: ParkingRepository): ViewModel() {

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    private val _validation = MutableLiveData<RegisterFieldState>()
    val validation: LiveData<RegisterFieldState> = _validation

    private val _userList = MutableLiveData<List<String>>()

    init {
        getAllUsersEmail()
    }

    fun registerUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            if(checkValidation(user)){
                try {
                    parkingRepository.registerUser(user)
                    _user.postValue(Resource.Success(user))
                }catch (e: Exception){
                    _user.postValue(Resource.Error(e.message.toString()))
                }
            }else{
                val registerFieldState = RegisterFieldState(
                    validationFirstName(user.firstName), validationLastName(user.lastName), validationEmail(user.email), validationPassword(user.password)
                )
                _validation.postValue(registerFieldState)
            }

        }
    }

    fun getAllUsersEmail(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = parkingRepository.getAllUsersEmail()
            _userList.postValue(result)
            Log.d("MyTag5", "getAllUsersEmail - $result")
        }
    }


    fun isEmailInList(email: String): Boolean{
//        Log.d("MyTag5", "${getAllUsersEmail().value}")
        return _userList.value?.contains(email) == true

    }


    private fun checkValidation(user: User): Boolean {
        val firstNameValidation = validationFirstName(user.firstName)
        val lastNameValidation = validationLastName(user.lastName)
        val emailValidation = validationEmail(user.email)
        val passwordValidation = validationPassword(user.password)
        val shouldRegister = firstNameValidation is RegisterValidation.Success && lastNameValidation is RegisterValidation.Success && emailValidation is RegisterValidation.Success
                && passwordValidation is RegisterValidation.Success
        return shouldRegister
    }
}
