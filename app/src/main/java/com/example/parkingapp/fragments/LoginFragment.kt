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
import androidx.fragment.app.viewModels
import com.example.parkingapp.ParkingActivity
import com.example.parkingapp.databinding.FragmentLoginBinding
import com.example.parkingapp.util.Constance
import com.example.parkingapp.util.LoginValidation
import com.example.parkingapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var bindingClass: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentLoginBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingClass.btnLogin.setOnClickListener {
            login()
        }

        checkValidation()


    }

    private fun login(){
         val sharedPref = requireActivity().getSharedPreferences(Constance.PREF_TABLE_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().putString(Constance.PREF_EMAIL_NAME, bindingClass.etEmail.text.toString()).apply()
         loginViewModel.loginWithEmailAndPassword(bindingClass.etEmail.text.toString(), bindingClass.etPassword.text.toString()).observe(viewLifecycleOwner){
             Log.d("MyTag", "User - $it")
             if(it != null){
                val intent = Intent(requireContext(), ParkingActivity::class.java)
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                 intent.putExtra("key", it.firstName)
                 startActivity(intent)
             }else if(bindingClass.etEmail.text.toString().isNotEmpty() && bindingClass.etPassword.text.toString().isNotEmpty()){
                 Toast.makeText(requireContext(), "Неправильна пошта або пароль", Toast.LENGTH_SHORT).show()
             }
         }

    }


    private fun checkValidation(){
        loginViewModel.validation.observe(viewLifecycleOwner){
            if(it.email is LoginValidation.Failed){
                bindingClass.etEmail.apply {
                    requestFocus()
                    error = it.email.message
                }
            }
            if(it.password is LoginValidation.Failed){
                bindingClass.etPassword.apply {
                    requestFocus()
                    error = it.password.message
                }
            }
        }
    }


}