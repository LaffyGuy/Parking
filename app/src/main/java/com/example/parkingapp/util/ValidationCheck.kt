package com.example.parkingapp.util

import android.util.Patterns

fun validationFirstName(firstName: String): RegisterValidation {
    if(firstName.isEmpty()){
        return RegisterValidation.Failed("Ви не вказали ім'я")
    }

    return RegisterValidation.Success
}

fun validationLastName(lastName: String): RegisterValidation {
    if(lastName.isEmpty()){
       return RegisterValidation.Failed("Ви не вказали прізвище")
    }
    return RegisterValidation.Success
}

fun validationEmail(email: String): RegisterValidation {
    if(email.isEmpty()){
       return RegisterValidation.Failed("Ви не вказали пошту")
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return RegisterValidation.Failed("Неправильний формат пошти")
    }
    return RegisterValidation.Success
}

fun validationPassword(password: String): RegisterValidation {
    if(password.isEmpty()){
       return RegisterValidation.Failed("Ви не вказали пароль")
    }
    if(password.length < 8){
        return RegisterValidation.Failed("Пароль повинен складатися з 8 або більше символів")
    }
    return RegisterValidation.Success
}

fun validationLoginEmail(email: String): LoginValidation {
    if(email.isEmpty()){
        return LoginValidation.Failed("Ви не вказали пошту")
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return LoginValidation.Failed("Неправильний формат пошти")
    }
    return LoginValidation.Success
}

fun validationLoginPassword(password: String): LoginValidation {
    if(password.isEmpty()){
        return LoginValidation.Failed("Ви не вказали пароль")
    }
    if(password.length < 8){
        return LoginValidation.Failed("Пароль повинен складатися з 8 або більше символів")
    }
    return LoginValidation.Success
}