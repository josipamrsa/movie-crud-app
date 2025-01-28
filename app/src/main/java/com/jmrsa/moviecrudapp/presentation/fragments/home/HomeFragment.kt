package com.jmrsa.moviecrudapp.presentation.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutHomeBinding
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseFragment
import com.jmrsa.moviecrudapp.presentation.models.AppMovie
import com.jmrsa.moviecrudapp.presentation.shared.adapters.image_carousel.ImageViewAdapter
import com.jmrsa.moviecrudapp.presentation.shared.adapters.movie_list.MovieListAdapter
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
                movieData.user?.imageUri.let {
                    imageProfile.load(it)
                    imageProfile.scaleType = ImageView.ScaleType.CENTER_CROP
                }

                rvMovieFavorites.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    adapter = ImageViewAdapter(
                        movieList = movieData.userFavorites.orEmpty(),
                        onImageClicked = ::navigateToMovieDetails
                    )
                }

                rvStaffPicks.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = MovieListAdapter(
                        movieList = movieData.staffPicks.orEmpty(),
                        onMovieItemClicked = ::navigateToMovieDetails
                    ) { appMovie ->
                        homeViewModel.onFavoriteClicked(
                            movieData.user,
                            appMovie,
                            movieData.userFavorites
                        )
                    }
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
                }
            }
        }
    }

    private fun navigateToSearch() {
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

    private fun navigateToMovieDetails(appMovie: AppMovie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsBottomSheet(movieDetails = appMovie)
        findNavController().navigate(action)
    }
}