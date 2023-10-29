package com.example.parkingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.parkingapp.R
import com.example.parkingapp.databinding.FragmentRegisterLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterLoginFragment : Fragment() {
    lateinit var bindingClass: FragmentRegisterLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentRegisterLoginBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingClass.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registerLoginFragment_to_registerFragment)
        }

        bindingClass.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerLoginFragment_to_loginFragment)
        }


    }


}