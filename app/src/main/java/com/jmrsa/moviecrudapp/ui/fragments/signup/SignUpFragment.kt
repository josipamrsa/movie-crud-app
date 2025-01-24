package com.jmrsa.moviecrudapp.ui.fragments.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutSignUpBinding
import com.jmrsa.moviecrudapp.ui.fragments.base.BaseFragment

class SignUpFragment : BaseFragment<LayoutSignUpBinding>() {

    override fun handleBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutSignUpBinding {
        return LayoutSignUpBinding.inflate(inflater, container, false)
    }

    override fun initView(binding: LayoutSignUpBinding) {
        binding.apply {
            buttonSignUp.setOnClickListener { navigateToDashboard() }
        }
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
    }
}