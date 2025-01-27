package com.jmrsa.moviecrudapp.presentation.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutHomeBinding
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseFragment
import com.jmrsa.moviecrudapp.presentation.fragments.home.details.DetailsBottomSheet
import com.jmrsa.moviecrudapp.presentation.models.AppMovie
import com.jmrsa.moviecrudapp.presentation.shared.adapters.image_carousel.ImageViewAdapter
import com.jmrsa.moviecrudapp.presentation.shared.adapters.movie_list.MovieListAdapter
import com.jmrsa.moviecrudapp.presentation.shared.adapters.movie_list.MovieListViewHolder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<LayoutHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModel()

    override fun handleBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutHomeBinding {
        return LayoutHomeBinding.inflate(inflater, container, false)
    }

    override fun initView(binding: LayoutHomeBinding) {
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this

        homeViewModel.viewState.observe(this) { movieData ->
            binding.apply {
                rvMovieFavorites.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    adapter = ImageViewAdapter(movieData.userFavorites.orEmpty()) { appMovie -> {} }
                }

                rvStaffPicks.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = MovieListAdapter(
                        movieData.staffPicks.orEmpty(),
                        ::navigateToMovieDetails
                    ) { appMovie -> {} }
                }
            }
        }

        binding.apply {
            buttonSearch.setOnClickListener {
                homeViewModel.onSearchClicked()
            }
        }

        lifecycleScope.launch {
            homeViewModel.effect.collectLatest { update ->
                when (update) {
                    HomeContract.Effect.NavigateToSearch -> navigateToSearch()
                    HomeContract.Effect.OpenDetails -> TODO()
                }
            }
        }
    }

    private fun navigateToSearch() {
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

    private fun navigateToMovieDetails(appMovie: AppMovie) {
        val modal = DetailsBottomSheet()
        parentFragmentManager.let { modal.show(it, DetailsBottomSheet.BOTTOM_SHEET_TAG) }
    }

}