package com.jmrsa.moviecrudapp.presentation.shared.adapters.image_carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmrsa.moviecrudapp.databinding.ItemCarouselBinding
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.presentation.models.AppMovie

class ImageViewAdapter(
    private var movieList: List<AppMovie>,
    private val onImageClicked: (AppMovie) -> Unit
) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCarouselBinding.inflate(layoutInflater, parent, false)
        return ImageViewHolder(binding, onImageClicked)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindImageItem(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}