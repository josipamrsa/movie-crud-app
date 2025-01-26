package com.jmrsa.moviecrudapp.ui.fragments.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutSignUpBinding
import com.jmrsa.moviecrudapp.ui.fragments.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<LayoutSignUpBinding>() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun handleBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutSignUpBinding {
        return LayoutSignUpBinding.inflate(inflater, container, false)
    }

    override fun initView(binding: LayoutSignUpBinding) {
        binding.viewmodel = signUpViewModel
        binding.lifecycleOwner = this

        binding.apply {
            buttonSignUp.setOnClickListener {
                signUpViewModel.onSignUpClicked(
                    name = binding.inputName.text.toString(),
                    email = binding.inputEmail.text.toString(),
                    password = binding.inputPassword.text.toString(),
                    confirmPassword = binding.inputConfirm.text.toString(),
                )
            }
        }
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
    }
}