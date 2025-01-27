package com.jmrsa.moviecrudapp.presentation.shared.adapters.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmrsa.moviecrudapp.databinding.ItemMovieBinding
import com.jmrsa.moviecrudapp.presentation.models.AppMovie

class MovieListAdapter(
    private var movieList: List<AppMovie>,
    private val onMovieItemClicked: (AppMovie) -> Unit,
    private val onFavoriteClicked: (AppMovie) -> Unit
) : RecyclerView.Adapter<MovieListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieListViewHolder(binding, onMovieItemClicked, onFavoriteClicked)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bindMovieItem(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}