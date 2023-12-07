package com.lucas.petros.spotfylab.features.artists.presentation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lucas.petros.commons.base.BaseFragmentVDB
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.FragmentArtistsBinding
import com.lucas.petros.spotfylab.features.artists.presentation.list.adapter.ArtistsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtistsFragment : BaseFragmentVDB<FragmentArtistsBinding>(R.layout.fragment_artists) {
    private val vm: ArtistsViewModel by viewModels()
    private var artistsAdapter: ArtistsAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerView()
    }

    override fun setupViewModel() {
        binding.vm = vm
    }

    override fun setupObservers() {
        observerArtistsPaging()
    }

    private fun observerArtistsPaging() {
        vm.pagingList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                lifecycleScope.launch {
                    list.collectLatest {
                        artistsAdapter?.submitData(it)
                    }
                }
                vm.showStopLoading()
            }
        }
    }

    private fun setupAdapter() {
        artistsAdapter = ArtistsAdapter() { artist ->
            artist?.id
        }
    }

    private fun setupRecyclerView() {
        binding.rvArtists.adapter = artistsAdapter
    }
}