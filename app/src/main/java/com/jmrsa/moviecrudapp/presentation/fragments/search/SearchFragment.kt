package com.jmrsa.moviecrudapp.presentation.fragments.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutSearchBinding
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseFragment
import com.jmrsa.moviecrudapp.presentation.fragments.home.HomeFragmentDirections
import com.jmrsa.moviecrudapp.presentation.models.AppMovie
import com.jmrsa.moviecrudapp.presentation.shared.adapters.movie_list.MovieListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment<LayoutSearchBinding>() {
    private val searchViewModel : SearchViewModel by viewModel()

    override fun handleBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutSearchBinding {
        return LayoutSearchBinding.inflate(inflater, container, false)
    }

    override fun initView(binding: LayoutSearchBinding) {
        binding.lifecycleOwner = this

        searchViewModel.viewState.observe(this) { movieData ->
            binding.apply {
                rvSearchResults.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = MovieListAdapter(
                        movieList = movieData.allMovies.orEmpty(),
                        onMovieItemClicked = ::navigateToMovieDetails
                    ) { appMovie ->
                        searchViewModel.onFavoriteClicked(
                            movieData.user,
                            appMovie,
                            movieData.userFavorites
                        )
                    }
                }
            }
        }

        binding.apply {
            buttonBack.setOnClickListener {
                navigateBack()
            }

            inputSearch.addTextChangedListener { editable ->
                searchViewModel.onSearchValueChanged(editable.toString())
            }
        }
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
    }

    private fun navigateToMovieDetails(appMovie: AppMovie) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsBottomSheet(appMovie)
        findNavController().navigate(action)
    }
}