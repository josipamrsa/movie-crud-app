package com.jmrsa.moviecrudapp.presentation.fragments.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutSignUpBinding
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<LayoutSignUpBinding>() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    private val pickMedia: ActivityResultLauncher<PickVisualMediaRequest> =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            signUpViewModel.onImageSelected(uri.toString())
            binding.imagePicker.load(uri)

            binding.imagePicker.apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

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

            imagePicker.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest
                    .Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    .build()
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