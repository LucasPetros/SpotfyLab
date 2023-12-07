package com.lucas.petros.spotfylab.features.artists.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lucas.petros.commons.base.BaseFragmentVDB
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.FragmentArtistDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistDetailFragment : BaseFragmentVDB<FragmentArtistDetailBinding>(R.layout.fragment_artist_detail) {

    private val vm:ArtistDetailViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViewModel() {
        binding.vm = vm
    }

    override fun setupObservers() {}
}