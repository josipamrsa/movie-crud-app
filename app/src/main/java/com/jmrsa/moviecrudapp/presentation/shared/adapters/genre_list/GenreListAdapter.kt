package com.jmrsa.moviecrudapp.presentation.shared.adapters.genre_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmrsa.moviecrudapp.databinding.ItemGenreChipBinding

class GenreListAdapter(private val genreList: List<String>) : RecyclerView.Adapter<GenreListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreChipBinding.inflate(layoutInflater, parent, false)
        return GenreListViewHolder(binding)
    }

    override fun getItemCount(): Int = genreList.size

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        holder.bindGenreItem(genreList[position])
    }
}