package com.jmrsa.moviecrudapp.presentation.shared.adapters.movie_list

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.ItemMovieBinding

class MovieListViewHolder(
    private val context: Context,
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bindMovieTitles(
        imageUrl: String,
        movieYear: String,
        movieTitle: String,
        movieRating: Double,
        isFavorited: Boolean = false
    ) {
        binding.apply {
            labelMovieTitle.text = movieTitle
            labelMovieYear.text = movieYear.toString()
            labelMovieRating.rating = movieRating.toFloat()
            imageMovieCover.load(imageUrl)
            buttonFavorite.setImageResource(
                if (isFavorited) R.drawable.ic_favorited_outline
                else R.drawable.ic_favorited_fill
            )
        }
    }
}