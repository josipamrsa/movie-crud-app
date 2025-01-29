package com.jmrsa.moviecrudapp.presentation.fragments.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutDetailsBinding
import com.jmrsa.moviecrudapp.presentation.shared.adapters.genre_list.GenreListAdapter
import com.jmrsa.moviecrudapp.presentation.shared.dialogs.ModalBottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsBottomSheetFragment : ModalBottomSheetDialogFragment<LayoutDetailsBinding>() {
    private val args: DetailsBottomSheetFragmentArgs by navArgs()
    private val detailsViewModel: DetailsViewModel by viewModel { parametersOf(args.movieDetails) }

    override fun handleBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutDetailsBinding {
        return LayoutDetailsBinding.inflate(inflater, container, false)
    }

    override fun initView(binding: LayoutDetailsBinding) {
        binding.viewmodel = detailsViewModel
        binding.lifecycleOwner = this

        binding.buttonClose.setOnClickListener {
            detailsViewModel.onCloseClicked()
        }

        detailsViewModel.viewState.observe(this) { movieDetails ->
            binding.apply {
                buttonFavorite.setImageResource(
                    if (movieDetails.movie.isFavorited) R.drawable.ic_favorited_fill
                    else R.drawable.ic_favorited_outline
                )

                imageMovieCoverLarge.load(movieDetails.movie.posterUrl)

                tvTitleYear.text = movieDetails.formattedTitle
                tvTimeInfo.text = movieDetails.formattedReleaseDate
                ratingMovieRatingDetails.rating = movieDetails.formattedRating

                rvGenres.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    adapter = GenreListAdapter(
                        genreList = movieDetails.movie.genres?.toList().orEmpty()
                    )
                }

                tvBudget.text = movieDetails.formattedBudget
                tvRevenue.text = movieDetails.formattedRevenue
                tvLanguage.text = movieDetails.formattedOriginalLanguage
                tvRating.text = movieDetails.formattedRatingDecimals

                buttonFavorite.setOnClickListener {
                    detailsViewModel.onFavoriteClicked(
                        movieDetails.user,
                        movieDetails.movie,
                        movieDetails.userFavorites
                    )
                }
            }
        }

        lifecycleScope.launch {
            detailsViewModel.effect.collectLatest { update ->
                when (update) {
                    DetailsContract.Effect.NavigateToHome -> navigateBack()
                    DetailsContract.Effect.NavigateToSignUp -> navigateToSignUp()
                }
            }
        }
    }

    private fun navigateToSignUp() {
        findNavController().navigate(R.id.action_detailsBottomSheet_to_signUpFragment)
    }

    private fun navigateBack() {
        val backStackEntry = findNavController().previousBackStackEntry

        backStackEntry?.destination?.id?.let {
            findNavController().navigate(it)
        }
    }

}