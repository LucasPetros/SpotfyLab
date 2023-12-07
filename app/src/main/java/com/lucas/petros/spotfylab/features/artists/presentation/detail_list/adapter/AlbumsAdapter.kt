package com.lucas.petros.spotfylab.features.artists.presentation.detail_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.AlbumItemBinding
import com.lucas.petros.spotfylab.features.artists.domain.model.Album

internal class AlbumsAdapter(

) : PagingDataAdapter<Album, AlbumsAdapter.DataHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.album_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.binding.run {
            val item = getItem(position)
            album = item
        }
    }

    inner class DataHolder(val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(
            oldItem: Album,
            newItem: Album
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Album,
            newItem: Album
        ) = oldItem == newItem
    }
}