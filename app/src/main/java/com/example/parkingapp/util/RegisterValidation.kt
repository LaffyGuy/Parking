package com.example.parkingapp.util

sealed class RegisterValidation{
    object Success: RegisterValidation()
    data class Failed(val message: String): RegisterValidation()
}
data class RegisterFieldState(
    val firstName: RegisterValidation,
    val lastName: RegisterValidation,
    val email: RegisterValidation,
    val password: RegisterValidation
)

sealed class LoginValidation{
    object Success: LoginValidation()
    data class Failed(val message: String): LoginValidation()
}

data class LoginFieldState(
    val email: LoginValidation,
    val password: LoginValidation
)

