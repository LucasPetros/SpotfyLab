package com.lucas.petros.spotfylab.features.playlists.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.PlaylistItemBinding
import com.lucas.petros.spotfylab.features.playlists.domain.model.Playlist

internal class PlaylistsAdapter(

) : PagingDataAdapter<Playlist, PlaylistsAdapter.DataHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.playlist_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.binding.run {
            val item = getItem(position)
            playlist = item
        }
    }

    inner class DataHolder(val binding: PlaylistItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<Playlist>() {

        override fun areItemsTheSame(
            oldItem: Playlist,
            newItem: Playlist
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Playlist,
            newItem: Playlist
        ) = oldItem == newItem
    }
}