package com.jmrsa.moviecrudapp.presentation.shared.adapters.genre_list

import androidx.recyclerview.widget.RecyclerView
import com.jmrsa.moviecrudapp.databinding.ItemGenreChipBinding

class GenreListViewHolder(
    private val binding: ItemGenreChipBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bindGenreItem(
        genre: String
    ) {
        binding.apply {
            tvGenreChip.text = genre
        }
    }
}