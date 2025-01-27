package com.jmrsa.moviecrudapp.presentation.shared.adapters.image_carousel

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmrsa.moviecrudapp.databinding.ItemCarouselBinding
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.presentation.models.AppMovie

class ImageViewHolder(
    private val binding: ItemCarouselBinding,
    private val onImageClick: (item: AppMovie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindImageItem(item: AppMovie) {
        binding.apply {
            imageFavoritedMovies.load(item.posterUrl)
            imageFavoritedMovies.setOnClickListener { onImageClick(item) }
        }
    }
}