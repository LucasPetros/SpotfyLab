package com.lucas.petros.spotfylab.features.artists.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.ArtistsItemBinding
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist

internal class ArtistsAdapter(
    private val onItemClick: (Artist?) -> Unit,
) : PagingDataAdapter<Artist, ArtistsAdapter.DataHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.artists_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.binding.run {
            val item = getItem(position)
            artist = item
            root.setOnClickListener { onItemClick(item) }
        }
    }

    inner class DataHolder(val binding: ArtistsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<Artist>() {

        override fun areItemsTheSame(
            oldItem: Artist,
            newItem: Artist
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Artist,
            newItem: Artist
        ) = oldItem == newItem
    }

}