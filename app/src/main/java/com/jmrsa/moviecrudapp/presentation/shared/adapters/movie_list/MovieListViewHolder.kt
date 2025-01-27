package com.jmrsa.moviecrudapp.presentation.shared.adapters.movie_list

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.ItemMovieBinding
import com.jmrsa.moviecrudapp.presentation.models.AppMovie

class MovieListViewHolder(
    private val binding: ItemMovieBinding,
    private val onMovieItemClicked: (AppMovie) -> Unit,
    private val onFavoriteClicked: (AppMovie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bindMovieItem(
        item: AppMovie
    ) {
        binding.apply {
            labelMovieTitle.text = item.title
            labelMovieYear.text = item.releaseDate?.take(4)
            labelMovieRating.rating = item.rating?.toFloat() ?: 0f
            imageMovieCover.load(item.posterUrl)
            buttonFavorite.setImageResource(
                if (false) R.drawable.ic_favorited_outline
                else R.drawable.ic_favorited_fill
            )
            sectionMovieInfo.setOnClickListener { onMovieItemClicked(item) }
            buttonFavorite.setOnClickListener { onFavoriteClicked(item) }
        }
    }
}