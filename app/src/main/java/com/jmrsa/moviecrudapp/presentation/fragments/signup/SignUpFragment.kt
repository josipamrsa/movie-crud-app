package com.jmrsa.moviecrudapp.presentation.fragments.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutSignUpBinding
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

        lifecycleScope.launch {
            signUpViewModel.effect.collectLatest { update ->
                when (update) {
                    SignUpContract.Effect.NavigateToHome -> navigateToHome()
                }
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
    }
}