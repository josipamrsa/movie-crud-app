package com.jmrsa.moviecrudapp.presentation.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutHomeBinding
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseFragment
import com.jmrsa.moviecrudapp.presentation.shared.adapters.image_carousel.ImageViewAdapter

class HomeFragment : BaseFragment<LayoutHomeBinding>() {
    override fun handleBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutHomeBinding {
        return LayoutHomeBinding.inflate(inflater, container, false)
    }

    override fun initView(binding: LayoutHomeBinding) {
        binding.apply {
            rvMovieFavorites.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = ImageViewAdapter(
                    listOf(
                        "https://images.unsplash.com/photo-1737365507770-5dccad417087?q=80&w=387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        "https://images.unsplash.com/photo-1737441835439-c1f5657d7c7f?q=80&w=880&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                    )
                )
            }

            buttonSearch.setOnClickListener {
                //val modal = DetailsBottomSheet()
                //parentFragmentManager.let { modal.show(it, DetailsBottomSheet.BOTTOM_SHEET_TAG) }
                navigateToSearch()
            }
        }
    }

    private fun navigateToSearch() {
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

}