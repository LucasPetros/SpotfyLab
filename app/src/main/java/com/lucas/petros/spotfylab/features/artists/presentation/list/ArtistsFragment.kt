package com.lucas.petros.spotfylab.features.artists.presentation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lucas.petros.commons.base.BaseFragmentVDB
import com.lucas.petros.commons.extension.handleOpt
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
        observerNewToken()
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

    private fun observerNewToken() {
        vm.stateToken.observe(viewLifecycleOwner) { tokenData ->
            if (tokenData.data?.accessToken?.isNotBlank() == true || tokenData.error.isNotBlank()) {
                vm.getArtists()
            }
        }
    }

    private fun setupAdapter() {
        artistsAdapter = ArtistsAdapter() { artist ->
            val direction = ArtistsFragmentDirections.toArtistDetailFragment()
            direction.id = artist?.id.handleOpt()
            direction.imageUrl = artist?.imageUrl.handleOpt()
            direction.name = artist?.name.handleOpt()
            findNavController().navigate(direction)
        }
    }

    private fun setupRecyclerView() {
        binding.rvArtists.adapter = artistsAdapter
    }
}