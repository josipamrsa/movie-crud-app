package com.jmrsa.moviecrudapp.presentation.fragments.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutSearchBinding
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseFragment

class SearchFragment : BaseFragment<LayoutSearchBinding>() {
    override fun handleBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutSearchBinding {
        return LayoutSearchBinding.inflate(inflater, container, false)
    }

    override fun initView(binding: LayoutSearchBinding) {
        binding.apply {
            buttonBack.setOnClickListener {
                navigateBack()
            }
        }
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
    }
}