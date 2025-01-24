package com.jmrsa.moviecrudapp.ui.fragments.home.adapters.image_carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.ItemCarouselBinding

class ImageViewAdapter(
    private val images: List<String>
) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCarouselBinding.inflate(layoutInflater, parent, false)
        return ImageViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindImageItem(images[position])
    }

    override fun getItemCount(): Int = images.size
}