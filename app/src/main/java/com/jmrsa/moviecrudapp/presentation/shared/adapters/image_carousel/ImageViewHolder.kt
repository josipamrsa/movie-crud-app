package com.jmrsa.moviecrudapp.presentation.shared.adapters.image_carousel

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmrsa.moviecrudapp.databinding.ItemCarouselBinding

class ImageViewHolder(
    private val context: Context,
    private val binding: ItemCarouselBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindImageItem(imageUrl: String) {
        binding.apply {
            imageFavoritedMovies.load(imageUrl)
        }
    }
}